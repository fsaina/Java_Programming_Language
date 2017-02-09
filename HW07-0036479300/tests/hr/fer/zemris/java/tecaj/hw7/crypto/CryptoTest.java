package hr.fer.zemris.java.tecaj.hw7.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CryptoTest {
	
	@Test
	public void testHexToByte1(){
		byte[] result = {0b01100010, 0b01101111, 0b01101011};
		byte[] data = Crypto.hextobyte("626F6B");
		
		for(int i =0; i< result.length ; i++){
			assertEquals(result[i], data[i]);
		}
		
		
	}
	
	@Test
	public void testHexToByte2(){
		byte[] result = {0b01101001, 0b01110011, 0b01110100, 0b01101111, 0b01001110, 0b01100101};
		byte[] data = Crypto.hextobyte("6973746F4E65");
		
		for(int i =0; i< result.length ; i++){
			assertEquals(result[i], data[i]);
		}
		
		
	}
	
	@Test
	public void testHexToByte3(){
		byte[] result = { (byte) 0b10101010, (byte) 0b10101010};
		byte[] data = Crypto.hextobyte("AAAA");
		
		for(int i =0; i< result.length ; i++){
			assertEquals(result[i], data[i]);
		}
		
		
	}

}
