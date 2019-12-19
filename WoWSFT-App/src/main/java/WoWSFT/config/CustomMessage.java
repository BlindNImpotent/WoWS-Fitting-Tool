package WoWSFT.config;

public class CustomMessage
{
    private String status;
    private String message = "";

    public CustomMessage()
    {

    }

    public CustomMessage(String status)
    {
        this.status = status;
    }

    public CustomMessage(String status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }
}
