import hse.cs163.chuev.gossip.Gossip;
import org.junit.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class GossipsTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void assert_create() throws Exception {
        Gossip g = Gossips.create("Message");
        assertEquals("Message", g.name());
    }

    @Test
    public void assert_linkAndUnlink() throws Exception {
        Gossip g1 = Gossips.create("g1");
        Gossip g2 = Gossips.create("g2");

        Gossips.link(g1, g2);
        assertEquals(g1.listeners().get(0), g2);

        Gossips.link(g1, g2);
        assertEquals(g1.listeners().size(), 0);
    }

    @Test
    public void message() throws Exception {
        Gossip g = new Gossip("TestName");
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
}