package hr.fer.zemris.java.simplecomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class InstructionsTests {
    @Mock
    private Computer computer;
    @Mock
    private Registers registers;
    @Mock
    private Memory memory;
    @Mock
    private InstructionArgument arg1;
    @Mock
    private InstructionArgument arg2;
    @Mock
    private InstructionArgument arg3;
    @Mock
    private InstructionArgument memoryAddress;
    @Mock
    private List<InstructionArgument> list;


    @Before
    public void setUp() {
	//set up computer
	when(computer.getRegisters()).thenReturn(registers);
	when(computer.getMemory()).thenReturn(memory);
	//Setup few registers values
	when(registers.getRegisterValue(1)).thenReturn(new Integer(20));
	when(registers.getRegisterValue(2)).thenReturn(new Integer(10));
	when(registers.getRegisterValue(3)).thenReturn(new Integer(10));
	//Set up registers
	//arg1
	when(arg1.isRegister()).thenReturn(true);
	when(arg1.isNumber()).thenReturn(false);
	when(arg1.isString()).thenReturn(false);
	//arg2
	when(arg2.isRegister()).thenReturn(true);
	when(arg2.isNumber()).thenReturn(false);
	when(arg2.isString()).thenReturn(false);
	//arg3
	when(arg3.isRegister()).thenReturn(true);
	when(arg3.isNumber()).thenReturn(false);
	when(arg3.isString()).thenReturn(false);
	//Memory address
	when(memoryAddress.isRegister()).thenReturn(false);
	when(memoryAddress.isNumber()).thenReturn(true);
	when(memoryAddress.isString()).thenReturn(false);
    }

    @Test
    public void retTest() {
	// Setting up arguments
	@SuppressWarnings("unchecked")
	List<InstructionArgument> arguments = (List<InstructionArgument>) mock(List.class);
	// no arguments required 
	when(arguments.size()).thenReturn(0);

	when(registers.getRegisterValue(15)).thenReturn(new Integer(10));
	when(memory.getLocation(11)).thenReturn(new Integer(30));

	Instruction instruction = new InstrRet(arguments);
	instruction.execute(computer);

	verify(registers, times(1)).getRegisterValue(15);
	verify(memory, times(1)).getLocation(11);
	verify(registers, times(1)).setProgramCounter(30);
	verify(registers, times(1)).setRegisterValue(15, 11);
    }
    
    //TEST LOAD
    @Test
    public void testLoadInstruction1() {
    	
    	when(arg1.getValue()).thenReturn(1);
    	when(arg1.isRegister()).thenReturn(true);
    	
    	when(arg2.getValue()).thenReturn(0);
    	
    	setArgumentList();
        
    	setMemoryValues();
    	
    	setComputerValues();
        
        Instruction load = new InstrLoad(list);
        boolean rez = load.execute(computer);
        
        verify(memory, times(1)).getLocation(0);
        assertEquals(false, rez);
        assertEquals(10, memory.getLocation(0));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testLoadInstructionForInvalidArguments() {
    	
    	when(arg1.getValue()).thenReturn(1);
    	when(arg1.isRegister()).thenReturn(false);
    	
    	when(arg2.getValue()).thenReturn(0);
    	
    	setArgumentList();
        
    	setMemoryValues();
    	
    	setComputerValues();
        
    	//throws
        Instruction load = new InstrLoad(list);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testLoadInstructionIllegalNumberOfArguments(){
    	when(list.size()).thenReturn(3);
    	
    	//throws
    	Instruction load = new InstrLoad(list);
    }
    
    //TEST MOVE
    @Test
    public void testMoveInstruction1() {
    	
    	when(arg1.getValue()).thenReturn(2);
    	when(arg1.isRegister()).thenReturn(true);
    	
    	when(arg2.getValue()).thenReturn(3);
    	when(arg2.isRegister()).thenReturn(true);
    	
    	when(list.size()).thenReturn(3);
  
    	
    	setArgumentList();
    	
    	when(registers.getRegisterValue(3)).thenReturn(5);
    	when(registers.getRegisterValue(2)).thenReturn(5);
    	
    	setComputerValues();
        
        Instruction move = new InstrMove(list);
        boolean rez = move.execute(computer);
        
        assertEquals(false, rez);
        verify(memory, times(0)).getLocation(0);
        assertEquals(5, registers.getRegisterValue(2));
    }
    
    @Test
    public void testLoadInstruction2() {
    	
    	when(arg1.getValue()).thenReturn(10);
    	when(arg1.isRegister()).thenReturn(true);
    	
    	when(arg2.getValue()).thenReturn(11);
    	when(arg2.isNumber()).thenReturn(true);
    	
    	setArgumentList();
    	
    	when(registers.getRegisterValue(10)).thenReturn(11);
    	
    	setComputerValues();
        
        Instruction move = new InstrMove(list);
        boolean rez = move.execute(computer);
        
        assertEquals(false, rez);
        verify(arg2, times(1)).isNumber();
        assertEquals(11, registers.getRegisterValue(10));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testFirstArugmentAnumber(){
    	when(arg1.getValue()).thenReturn(1);
    	when(arg1.isRegister()).thenReturn(false);
    	
    	when(arg2.getValue()).thenReturn(0);
    	
    	setArgumentList();
        
    	setMemoryValues();
    	
    	setComputerValues();
        
        Instruction move = new InstrMove(list);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testMoveInstructionForInvalidArguments() {
    	
    	when(arg1.getValue()).thenReturn(1);
    	when(arg1.isRegister()).thenReturn(false);
    	
    	when(arg2.getValue()).thenReturn(0);
    	
    	setArgumentList();
        
    	setMemoryValues();
    	
    	setComputerValues();
        
        Instruction move = new InstrMove(list);
    }
    
    //TEST PUSH
    
    @Test
    public void testPushInstruction1() {
    	
    	when(arg1.getValue()).thenReturn(2);
    	when(arg1.isRegister()).thenReturn(true);
    	when(list.size()).thenReturn(1);
 
    	setArgumentListOne();
    	
    	when(registers.getRegisterValue(2)).thenReturn(200);
    	
    	setComputerValues();
    	
    	when(registers.getRegisterValue(15)).thenReturn(200);
    	when(memory.getLocation(15)).thenReturn(10);
        
        Instruction push = new InstrPush(list);
        boolean rez = push.execute(computer);
        
        assertEquals(false, rez);
        verify(memory, times(0)).getLocation(0);
        verify(memory, times(1)).setLocation(200, 200);
        verify(registers, times(1)).getRegisterValue(15);
        assertEquals(200, registers.getRegisterValue(2));
    }
    
    //TEST POP
    
    @Test
    public void testPopInstruction1() {
    	
    	when(arg1.getValue()).thenReturn(2);
    	when(arg1.isRegister()).thenReturn(true);
    	when(list.size()).thenReturn(1);
 
    	setArgumentListOne();
    	
    	when(registers.getRegisterValue(2)).thenReturn(200);
    	
    	setComputerValues();
    	
    	when(registers.getRegisterValue(15)).thenReturn(200);
    	when(memory.getLocation(15)).thenReturn(10);
        
        Instruction pop = new InstrPush(list);
        boolean rez = pop.execute(computer);
        
        assertEquals(false, rez);
        verify(memory, times(0)).getLocation(0);
        verify(memory, times(1)).setLocation(200, 200);
        verify(registers, times(1)).getRegisterValue(15);
        assertEquals(200, registers.getRegisterValue(2));
    }
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testFirstPushArugmentAnumber(){
    	when(arg1.getValue()).thenReturn(1);
    	when(arg1.isRegister()).thenReturn(false);
    	
    	when(arg2.getValue()).thenReturn(0);
    	
    	setArgumentList();
        
    	setMemoryValues();
    	
    	setComputerValues();
        
        Instruction push = new InstrPush(list);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testPushInstructionForInvalidArguments() {
    	
    	when(arg1.getValue()).thenReturn(1);
    	when(arg1.isRegister()).thenReturn(false);
    	
    	when(arg2.getValue()).thenReturn(0);
    	
    	setArgumentList();
        
    	setMemoryValues();
    	
    	setComputerValues();
        
        Instruction push = new InstrPush(list);
    }
    
    // TEST CALL
    
    @Test
    public void callTest() {
	//Setting up arguments
	@SuppressWarnings("unchecked")
	List<InstructionArgument> arguments = (List<InstructionArgument>) mock(List.class);
	
	//arguments set up
	when(arguments.size()).thenReturn(1);
	when(arguments.get(0)).thenReturn(memoryAddress);
	when(memoryAddress.getValue()).thenReturn(new Integer(6));

	//Setting up program counter
	when(registers.getProgramCounter()).thenReturn(new Integer(3));
	when(registers.getRegisterValue(new Integer(15))).thenReturn(37); // on index 15 is StackPointer
	
	//execute test
	Instruction instruction = new InstrCall(arguments);
	instruction.execute(computer);

	//Verify test
	verify(registers, times(1)).getProgramCounter();
	verify(memory, times(1)).setLocation(37, 3);
	verify(registers, times(1)).setRegisterValue(15, 36);
	verify(registers, times(1)).getRegisterValue(15); //index 15 is SP
	verify(registers, times(1)).setProgramCounter(6);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testPushInstructionIllegalNumberOfArguments(){
    	when(list.size()).thenReturn(3);
    	Instruction move = new InstrPush(list);
    }

	private void setComputerValues() {
		when(computer.getMemory()).thenReturn(memory);
        when(computer.getRegisters()).thenReturn(registers);
	}

	private void setMemoryValues() {
		when(memory.getLocation(0)).thenReturn(10);
	}

	private void setArgumentList() {
		when(list.get(0)).thenReturn(arg1);
    	when(list.get(1)).thenReturn(arg2);
    	when(list.size()).thenReturn(2);
	}
	
	private void setArgumentListOne(){
		when(list.get(0)).thenReturn(arg1);
    	when(list.size()).thenReturn(1);
	}
}
