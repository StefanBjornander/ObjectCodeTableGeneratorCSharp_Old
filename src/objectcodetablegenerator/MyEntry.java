package objectcodetablegenerator;
import java.util.*;

public class MyEntry<KeyType,ValueType> implements Map.Entry<KeyType,ValueType>{
  private KeyType m_key;
  private ValueType m_value;

  public MyEntry(KeyType key, ValueType value) {
    m_key = key;
    m_value = value;
  }

  public boolean equals(Object object) {
    if (object instanceof Map.Entry) {
      Map.Entry entry = (Map.Entry) object;
      return (((m_key == null) && (entry.getKey() == null)) ||
              ((m_key != null) && (entry.getKey() != null) &&
                m_key.equals(entry.getKey()))) &&
             (((m_value == null) && (entry.getValue() == null)) ||
              ((m_value != null) && (entry.getValue() != null) &&
                m_value.equals(entry.getValue())));
    }
    
    return false;
  }
 
  public KeyType getKey() {
    return m_key;
  }

  public ValueType getValue() {
    return m_value;
  }

  public int hashCode() {
    return ((m_key != null) ? m_key.hashCode() : 0) +
           ((m_value != null) ? m_value.hashCode() : 0);
  }

  public ValueType setValue(ValueType value) {
    ValueType oldValue = m_value;
    m_value = value;
    return oldValue;
  }

  public String toString() {
    if (m_value instanceof byte[]) {
      StringBuilder buffer = new StringBuilder("(" + ((m_key != null) ? m_key.toString() : "null") + " = [");
      
      boolean first = true;
      for (byte b : (byte[]) m_value) {
        buffer.append((first ? "" : ",") + b);
        first = false;
      }
      
      return buffer.toString() + "])";
    }
    else {
      return "(" + ((m_key != null) ? m_key.toString() : "null") + " = " + ((m_value != null) ? m_value.toString() : "null") + ")";
    }
  }

  /*public void readExternal(ObjectInput objectInput) {
    try {
      m_key = (KeyType) objectInput.readObject();
      m_value = (ValueType) objectInput.readObject();
    }
    
    catch (ClassNotFoundException exception) {
      Assert.error("entry read error", exception.getMessage());
    }
    catch (IOException ioException) {
      Assert.error("entry read error", ioException.getMessage());
    }
  }

  public void writeExternal(ObjectOutput objectOutput) {
    try {
      objectOutput.writeObject(m_key);
      objectOutput.writeObject(m_value);
    }
    catch (IOException ioException) {
      Assert.error("entry write error", ioException.getMessage());
    }
  }*/
}
