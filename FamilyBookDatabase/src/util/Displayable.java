package util;

public interface Displayable {
    // Graphing necessities
    double getX();
    double getY();

    void setX(double x);
    void setY(double y);
    // In practice I want the starting node as zero, this could also be an area that allows me to utilize multithreading (shared memory) (but that's also more of an optimization problem)
}