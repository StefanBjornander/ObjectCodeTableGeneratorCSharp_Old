package objectcodetablegenerator;
import java.util.*;

public class ListMap<KeyType,ValueType> implements Map<KeyType,ValueType> {
  private List<Map.Entry<KeyType,ValueType>> m_list;

  public ListMap() {
    m_list = new LinkedList<>();
  }

  public void ListMap(Map<KeyType,ValueType> map) {
    putAll(map);
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

  public boolean containsKey(Object key) {
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      if (key.equals(entry.getKey())) {
        return true;
      }
    }

    return false;
  }

  public boolean containsValue(Object value) {
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      if (value.equals(entry.getValue())) {
        return true;
      }
    }
    return false;
  }

  public ValueType get(Object key) {
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      if (key.equals(entry.getKey())) {
        return entry.getValue();
      }
    }
    
    return null;
  }

  public ValueType put(KeyType key, ValueType value) {
    ValueType result = null;
    
    for (int index = 0; index < m_list.size(); ++index) {
      Map.Entry<KeyType,ValueType> entry = m_list.get(index);
      
      if (key.equals(entry.getKey())) {
        result = entry.setValue(value);
        m_list.remove(index);
        break;
      }
    }
    
    m_list.add(new MyEntry(key, value));
    return result;
  }

  /*public ValueType put(KeyType key, ValueType value) {
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      if (key.equals(entry.getKey())) {
        return entry.setValue(value);
      }
    }
    
    m_list.add(new MyEntry(key, value));
    return null;
  }*/

  public void putAll(Map<? extends KeyType,?extends ValueType> map) {
    for (Map.Entry<? extends KeyType,?extends ValueType> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  public Map<KeyType,ValueType> clone() {
    ListMap<KeyType,ValueType> resultMap = new ListMap<>();
    resultMap.putAll(this);
    return resultMap;
  }
  
  public ValueType remove(Object key) {
    if (key != null) {
      Iterator<Map.Entry<KeyType,ValueType>> iterator = m_list.iterator();

      while (iterator.hasNext()) {
        Map.Entry<KeyType,ValueType> entry = iterator.next();

        if (key.equals(entry.getKey())) {
          ValueType oldValue = entry.getValue();
          iterator.remove();
          return oldValue;
        }
      }
    }

    return null;
  }

  public Set<KeyType> keySet() {
    Set result = new ListSet<>(); // ListSetXXX
    
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      result.add(entry.getKey());
    }
    
    return result;
  }

  public Collection<ValueType> values() {
    Collection result = new LinkedList<>();
    
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      result.add(entry.getValue());
    }
    
    return result;
  }

  public Set<Map.Entry<KeyType,ValueType>> entrySet() {
    Set result = new ListSet<>(); // ListSetXXX
    
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      result.add(entry);
    }

    return result;
  }

  /*public int hashCode() {
    int sum = 0;
    
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      sum += entry.hashCode();
    }
    
    return sum;
  }*/

  /*public int hashCode(){return 1;}*/
  
  public boolean equals(Object object) {
    if (object instanceof Map) {
      Map map = (Map) object;

      if (map.size() != m_list.size()) {
        return false;
      }
      
      for (Map.Entry<KeyType,ValueType> entry : m_list) {
        KeyType key = entry.getKey();
        ValueType value = entry.getValue();

        if (!map.containsKey(key) || !map.get(key).equals(value)) {
          return false;
        }
      }

      return true;
    }

    return false;
  }

  public String toString() {
    boolean first = true;
    StringBuilder buffer = new StringBuilder();
    for (Map.Entry<KeyType,ValueType> entry : m_list) {
      buffer.append((first ? "" : "\n") + entry.toString());
      first = false;
    }
    
    return "[" + buffer.toString() + "]";
  }
}