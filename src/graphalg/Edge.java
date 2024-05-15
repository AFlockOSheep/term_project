package graphalg;

public class Edge {
    private Object vertex1;
    private Object vertex2;
    private int weight;

    public Edge(Object vertex1, Object vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public Object getVertex1() {
        return vertex1;
    }
    public void setVertex1(Object vertex1) {
        this.vertex1 = vertex1;
    }

    public Object getVertex2() {
        return vertex2;
    }
    public void setVertex2(Object vertex2) {
        this.vertex2 = vertex2;
    }

}
