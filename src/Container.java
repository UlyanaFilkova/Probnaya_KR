import java.util.ArrayList;
import java.util.Collections;

public class Container<T extends Automobile> extends ArrayList<T> {
    public Container() {
        super();
    }


    public T max() throws EmptyException {
        if (this.isEmpty()) {
            throw new EmptyException();
        } else {
            return Collections.max(this);
        }
    }
    public T minimum() throws EmptyException {
        if (this.isEmpty())
            throw new EmptyException();
        return Collections.min(this);
    }

    public int frequency(T o) throws EmptyException {
        if (this.isEmpty()) {
            throw new EmptyException();
        } else {
            return Collections.frequency(this, o);
        }
    }

    public int search(T o) throws EmptyException {
        if (this.isEmpty()) {
            throw new EmptyException();
        } else {
            Container<T> temp = (Container)this.clone();
            Collections.sort(temp);
            int index = Collections.binarySearch(temp, o);
            if (index != -1) {
                return this.indexOf(temp.get(index));
            }else {
                return -1;
            }
        }
    }
}
class EmptyException extends Exception {
    public EmptyException() {
        super("There is no elements in Container!");
    }
}