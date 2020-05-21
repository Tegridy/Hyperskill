package maze;

public class Edge {

    private Vertex firstVertex;
    private Vertex secondVertex;

    private int weight;

    // I did not want to check both Vertices every time, so I created a function in Edge to do this
    public boolean containsVertex(Vertex Vertex){

        if(Vertex.equals(firstVertex) || Vertex.equals(secondVertex)){

            return true;

        }
        else {

            return false;

        }

    }

    // Two edges in a undirected graph are equal, if they contain the same Vertices
    // the order don't matter. to spare me the eternal comparison, i overwrote the equal method.
    // the weight does not matter in this case
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return this.containsVertex(edge.firstVertex) &&
                this.containsVertex(edge.secondVertex);
    }

    public Vertex getFirstVertex() {
        return firstVertex;
    }

    public void setFirstVertex(Vertex firstVertex) {
        this.firstVertex = firstVertex;
    }

    public Vertex getSecondVertex() {
        return secondVertex;
    }

    public void setSecondVertex(Vertex secondVertex) {
        this.secondVertex = secondVertex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
