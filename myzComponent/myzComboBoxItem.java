package myzComponent;

/**
 *
 * @author yazan
 */
public class myzComboBoxItem implements Comparable<myzComboBoxItem>
{ 
    private String m_value;
    private int    m_key;
    private Object m_extraData = null;
    
    public myzComboBoxItem(String value , int key)
    {
        m_value = value;
        m_key   = key;
    } 
    public myzComboBoxItem(String value , int key , Object extraData)
    {
        m_value     = value;
        m_key       = key;
        m_extraData = extraData;
    }
    
    public void setExtraData(Object extraData)
    {
        m_extraData = extraData;
    }
    
    public Object getExtraData()
    {
        return m_extraData;
    }

    @Override
    public int compareTo(myzComboBoxItem o) 
    {
        return this.m_key - o.getkey();
    }
    
    public int getkey()
    {
        return m_key;
    }
    
    public String getValue()
    {
        return m_value;
    }
    
    public String toString()
    {
        return m_value;
    }
    
    
}
