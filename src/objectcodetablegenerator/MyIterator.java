package objectcodetablegenerator;
import java.util.*;

public class MyIterator<IteratorType> implements Iterator {
  private int m_index;
  private List<IteratorType> m_list;

  public MyIterator(List<IteratorType> list) {
    m_index = 0;
    m_list = list;
  }

  public boolean hasNext() {
    return (m_index < m_list.size());
  }

  public IteratorType next() {
    return m_list.get(m_index++);
  }

  public void remove() {
    m_list.remove(--m_index);
  }
}