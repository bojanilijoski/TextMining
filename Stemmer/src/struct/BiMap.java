package struct;

import java.util.LinkedHashMap;

public class BiMap <K, V>
{
	private LinkedHashMap<K, V> keyToVal_;
    private LinkedHashMap<V, K> valToKey_;
    
    public BiMap()
    {
    	keyToVal_=new LinkedHashMap<>();
    	valToKey_=new LinkedHashMap<>();
    }
    
    public void add(K k, V v) {
        if (!keyToVal_.containsKey(k) && !valToKey_.containsKey(v)) {
            keyToVal_.put(k, v);
            valToKey_.put(v, k);
        }
    }
    
    public K getKey(V v)
    {
    	return valToKey_.get(v);
    }
    
    public V get(K k)
    {
    	return keyToVal_.get(k);
    }
    
    public String toString()
    {
    	return keyToVal_.toString();
    }
}
