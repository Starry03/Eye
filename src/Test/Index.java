package Test;

import Eye.Response.Response;
import Eye.Route.SocketConnection;

import java.io.IOException;

public class Index extends SocketConnection {
	public Index() {
		super("/");
		setProtected(false);
	}

	@Override
	protected Response response() {
		int i = 0;
		while (i < 10)
		{
			try {
				send("ciao");
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
			i++;
		}
		return null;
	}
}
