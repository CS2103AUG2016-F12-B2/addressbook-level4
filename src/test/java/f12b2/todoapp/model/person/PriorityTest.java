package f12b2.todoapp.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import f12b2.todoapp.model.person.Priority;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        //valid priority
        assertTrue(Priority.isValidPriority(0)); //zero
        assertTrue(Priority.isValidPriority(10)); //integer

    }
}
