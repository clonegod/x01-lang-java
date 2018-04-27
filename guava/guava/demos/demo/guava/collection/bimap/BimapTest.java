package demo.guava.collection.bimap;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
public class BimapTest {
	
	/**
	 *  keeps the values unique in the map as well as the keys
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBiMapUniqueValue() {
		BiMap<String,String> biMap = HashBiMap.create();
		biMap.put("1", "Tom");
		// This call cause an IllegalArgumentException to be thrown!
		biMap.put("2", "Tom");
	}
	
	/**
	 *  quietly remove the map entry with the same value before placing the key-value pair in the map
	 */
	@Test
	public void testBiMapForcePut() {
		BiMap<String,String> biMap = HashBiMap.create();
		biMap.put("1","Tom");
		biMap.forcePut("2","Tom"); // will remove entry which contains same value 'Tom' before put
		assertThat(biMap.containsKey("1"), is(false));
		assertThat(biMap.containsKey("2"), is(true));
	}
	
	/**
	 * Inverse key-value pair, use value as key
	 */
	@Test
	public void testBiMapInverse() {
		BiMap<String,String> biMap = HashBiMap.create();
		biMap.put("1","Tom");
		biMap.put("2","Harry");
		assertThat(biMap.get("1"), is("Tom"));
		assertThat(biMap.get("2"), is("Harry"));
		
		BiMap<String,String> inverseMap = biMap.inverse();
		assertThat(inverseMap.get("Tom"), is("1"));
		assertThat(inverseMap.get("Harry"), is("2"));
	}
}
