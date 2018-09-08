import hse.cs163.chuev.gossip.Gossip;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class GossipTest {
    Gossip g;
    @Before
    public void setUp() throws Exception {
        g = new Gossip("TestName");
    }

    @Test
    public void assert_name() throws Exception {
        assertEquals("TestName",  g.name());
    }

    @Test
    public void assert_AddAndGetAndRemoveListeners() throws Exception {
        Gossip g1 = new Gossip("Vova");
        Gossip g2 = new Gossip("Vanya");

        g.addListener(g1);
        g.addListener(g2);
        ArrayList<Gossip> listeners = g.listeners();
        assertEquals(2,  listeners.size());
        assertEquals(g1,  listeners.get(0));
        assertEquals(g2,  listeners.get(1));

        g.removeListener(g1);
        listeners = g.listeners();
        assertEquals(1,  listeners.size());
        assertEquals(g2,  listeners.get(0));
    }

    @Test
    public void assert_toString() throws Exception {
        String gString = g.toString();
        assertEquals("I am \'TestName\'. I don't tell stories", g.toString());
    }

    @Test
    public void assert_getMessage() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Gossip g1 = new Gossip("Vova");
        Gossip g2 = new Gossip("Vanya");
        g.addListener(g1);
        g.addListener(g2);

        g.getMessage("o", 1);
        String[] output = out.toString().split("\r\n");
        assertEquals(3, output.length);
        assertEquals("I am \'TestName\'. My message number 1: o", output[0]);
        assertTrue(output[1].equals("I am \'Vova\'. My message number 2: o") ||
                output[1].equals("I am \'Vanya\'. My message number 3: o"));
    }

    @Test
    public void compareTo() throws Exception {
        Gossip g1 = new Gossip("A");
        Gossip g2 = new Gossip("TestName");
        Gossip g3 = new Gossip("Z");

        assertTrue(g.compareTo(g1) > 0);
        assertTrue(g.compareTo(g2) == 0);
        assertTrue(g.compareTo(g3) < 0);
    }

}