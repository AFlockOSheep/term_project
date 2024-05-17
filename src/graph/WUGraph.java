/* WUGraph.java
 * Shaniel Singh
 * */

package graph;

import java.util.HashMap;
import java.util.Map;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
  /** Map from a vertex to its neighbors along with the associated weights.
   *  Keys in the outer map are the starting vertices and the values are another map
   *  with keys for destination vertices and values representing the weights
   *  the structure is as follows below.
   *
   *EX:
   * {
   *   "A": {
   *     "C": 1,
   *     "B": 1,
   *     "G": 3
   *   }
   *   "B": {
   *     "A": 1
   *   }
   *   "C": {
   *     "A": 1
   *   },
   *   "D": {}
   *   "G": {
   *     "A": 3
   *   }
   * }
   *
   **/
  private Map<Object, Map<Object, Integer>> graph;

  private int numEdges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   * HashMaps offer easy lookup in constant time since everything is bound by a key so it is efficient for this graph implementation
   * In order to have constant time for the edgeCount we keep track of it. Here we initialize a count to o to start.
   */
  public WUGraph() {
    this.graph = new HashMap<>();
    this.numEdges = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   * With the vertex count we can simply use the size method to return all the keys aka vertices in our graph
   */
  public int vertexCount() {
    return this.graph.size();
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   * Above we kept track of a variable for the edges allowing to use this method in constant time.
   * The use of a for loop here would have slowed down the runtime so instead we tracked the count manually
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
    return this.numEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Here since we want the number of vertices we take the keySet method giving us a set of all the keys in our graph
   * representing the total vertice count, then we use the toArray method to funnel it into an array as asked
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    return this.graph.keySet().toArray();
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   * Here we first check before adding if the provided argument is even a valid vertex, we do so using the isVertex implemented later
   * If the given parameter is not already a vertex, then we add it to the hashmap via the put method
   */
  public void addVertex(Object vertex) {
    if (!this.isVertex(vertex)) {
      this.graph.put(vertex, new HashMap<>());
    }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   * Here we first check before adding if the provided argument is even a valid vertex, we do so using the isVertex implemented later
   * If it is a valid vertex then we initialize an Object array with all the destination vertices for that vertex.
   * we do so by using the get method to get the vertex (k) along with the keySet method for the values to retrieve all the keys in the inner map representing the destVertices
   * Finally we funnel this into an array, loop over it and remove whereever this vertex may be end at
   * We also remove the vertex in the outer map as well and decrement the numEdges count by the length of destVertices since we now removed those connections
   */
  public void removeVertex(Object vertex) {
    if (this.isVertex(vertex)) {
      Object[] destVertices = this.graph.get(vertex).keySet().toArray();
      for (Object destVertex : destVertices) {
        this.graph.get(destVertex).remove(vertex);
      }
      this.graph.remove(vertex);
      this.numEdges -= destVertices.length;
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   * This method uses the containsKey method to check if our map contains the specified vertex and returns a boolean
   */
  public boolean isVertex(Object vertex) {
    return this.graph.containsKey(vertex);
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   * this first checks using an if statement if the vertex is a valid one or not using the isVertex method from earlier
   * if it isn't valid, then we simply return 0 as asked
   * If it is valid, then we use the get method on that vertex to return all of its connections and use the size method to get the total
   */
  public int degree(Object vertex) {
    if (!this.isVertex(vertex)) {
      return 0;
    }
    return this.graph.get(vertex).size();
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   * The neighbors list is set equal to destVertices and the weightlist is set equal to an array of equal length to destVertices
   * we loop over destVertices and swap the values in the array with the destVertices which would be the neighbors and finally return it
   */
  public Neighbors getNeighbors(Object vertex) {
    if (!this.isVertex(vertex)) {
      return null;
    }

    Object[] destVertices = this.graph.get(vertex).keySet().toArray();

    if (destVertices.length == 0) {
      return null;
    }

    Neighbors neighbors = new Neighbors();
    neighbors.neighborList = destVertices;
    neighbors.weightList = new int[destVertices.length];
    for (int i = 0; i < destVertices.length; i++) {
      neighbors.weightList[i] = this.graph.get(vertex).get(destVertices[i]);
    }
    return neighbors;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u.equals(v)) are allowed.
   *
   *in order to add an edge we first check if the passed in values are true vertices or not, if not then we return nothing
   *if the object U does not contain the key V then we are going to create that connection by incrementing the edge count by 1
   *then since this is a undirected graph we have to add the edge both ways
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
    if (!this.isVertex(u) || !this.isVertex(v)) {
      return;
    }
    if (!this.graph.get(u).containsKey(v)) {
      this.numEdges++;
    }
    this.graph.get(u).put(v, weight);
    this.graph.get(v).put(u, weight);
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   * first we check if this is not a edge, then we return nothing
   * otherwise, we remove the edges both ways and decrement by 1 to show the removal of the edge
   */
  public void removeEdge(Object u, Object v) {
    if (!this.isEdge(u, v)) {
      return;
    }
    this.graph.get(u).remove(v);
    this.graph.get(v).remove(u);
    this.numEdges--;
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   * here we have a boolean that returns false when checking if either object is not a valid vertex
   * it returns true if the key is present
   */
  public boolean isEdge(Object u, Object v) {
    if (!this.isVertex(u) || !this.isVertex(v)) {
      return false;
    }
    return this.graph.get(u).containsKey(v);
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.
   * here we first check if there is an edge for the passed in objects, if there is then we get u and we get the mapping for the values representing the weight
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    if (!this.isEdge(u, v)) {
      return 0;
    }
    return this.graph.get(u).get(v);
  }
}