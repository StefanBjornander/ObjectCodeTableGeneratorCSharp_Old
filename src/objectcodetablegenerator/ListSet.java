package objectcodetablegenerator;
import java.util.*;

public class ListSet<SetType> implements Set<SetType> {
  private List<SetType> m_list;

  public ListSet() {
    m_list = new LinkedList<>();
  }

  public ListSet(SetType value) {
    m_list = new LinkedList<>();
    m_list.add(value);
  }
  
  /*public ListSet(SetType value1, SetType value2) {
    m_list = new LinkedList<>();
    m_list.add(value1);
    m_list.add(value2);
  }*/
  
  public ListSet(SetType[] array) {
    m_list = new LinkedList<>();
    for (SetType value : array) {
      m_list.add(value);
    }
  }

  public ListSet(Collection<SetType> collection) {
    m_list = new LinkedList<>();
    for (SetType value : collection) {
      m_list.add(value);
    }
  }
  
  public Set<SetType> clone() {
    Set<SetType> resultSet = new ListSet<>(); // ListSetXXX
    resultSet.addAll(this);
    return resultSet;
  }
  
  public void clear() {
    m_list.clear();
  }

  public boolean isEmpty() {
    return m_list.isEmpty();
  }

  public int size() {
    return m_list.size();
  }

  public Iterator<SetType> iterator() {
    return (new MyIterator(m_list));
  }

  public boolean contains(Object object) {
    for (Object member : m_list) {
      if (member.equals(object)) {
        return true;
      }
    }
    
    return false;
  }

  public boolean containsAll(Collection<?> collection) {
    for (Object value : collection) {
      if (!contains(value)) {
        return false;
      }
    }

    return true;
  }

  public boolean add(SetType object) {
    if (!contains(object)) {
      m_list.add(object);
      return true;
    }

    return false;
  }

  public boolean addAll(Collection<? extends SetType> collection) {
    boolean change = false;

    for (SetType value : collection) {
      if (add(value)) {
        change = true;
      }
    }

    return change;
  }

  public boolean remove(Object object) {
    return m_list.remove(object);
  }

  public boolean removeAll(Collection<?> collection) {
    boolean change = false;
    Iterator iterator = m_list.iterator();

    while (iterator.hasNext()) {
      if (collection.contains(iterator.next())) {
        change = true;
        iterator.remove();
      }
    }

    return change;
  }

  public boolean retainAll(Collection<?> collection) {
    boolean change = false;
    Iterator iterator = m_list.iterator();

    while (iterator.hasNext()) {
      if (!collection.contains(iterator.next())) {
        change = true;
        iterator.remove();
      }
    }

    return change;
  }

  public boolean equals(Object object) {
    if (object instanceof Set) {
      Set set = (Set) object;

      if (m_list.size() != set.size()) {
        return false;
      }

      for (Object value : set) {
        if (!contains(value)) {
          return false;
        }
      }

      return true;
    }

    return false;
  }

  /*public int hashCode() {
    int sum = 0;

    for (Object value : m_list) {
      sum += (value != null) ? value.hashCode() : 0;
    }

    return sum;
  }*/

  public Object[] toArray() {
    int index = 0;
    Object[] result = new Object[m_list.size()];
    Iterator iterator = m_list.iterator();

    while (iterator.hasNext()) {
      result[index++] = iterator.next();
    }

    return result;
  }

  public <T> T[] toArray(T[] array) {
    int index = 0;

    for (SetType value : m_list) {
      array[index++] = (T) value;
    }

    return array;
  }

  public String toString() {
    boolean first = true;
    StringBuilder buffer = new StringBuilder();

    for (Object value : m_list) {
      buffer.append((first ? "" : ",") + value.toString());
      first = false;
    }

    return "{" + buffer.toString() + "}";
  }

  /*public void readExternal(ObjectInput objectInput) {
    try {
      int size = objectInput.readInt();
      for (int index = 0; index < size; ++index) {
        Object object = objectInput.readObject();
        m_list.add((SetType) object);
      }
    }
    catch (ClassNotFoundException exception) {
      Assert.error("set read error", exception.toString());
    }
    catch (IOException ioException) {
      Assert.error("set read error", ioException.toString());
    }
  }

  public void writeExternal(ObjectOutput objectOutput) {
    try {
      objectOutput.write(m_list.size());
      for (SetType value : m_list) {
        objectOutput.writeObject(value);
      }
    }
    catch (IOException ioException) {
      Assert.error("set write error", ioException.toString());
    }
  }*/
}