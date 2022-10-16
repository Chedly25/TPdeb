import java.io.* ;
class MyObjectInputStream extends ObjectInputStream {

    // Constructor of this class
    // 1. Default
    MyObjectInputStream() throws IOException
    {

        // Super keyword refers to parent class instance
        super();
    }

    // Constructor of this class
    // 1. Parameterized constructor
    MyObjectInputStream(InputStream i) throws IOException
    {
        super(i);
    }

    // Method of this class
    public void writeStreamHeader() throws IOException
    {
        return;
    }
}
