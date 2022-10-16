package core.client.view;

import java.util.*;

@FunctionalInterface
public interface ViewMapper {
	String act(List<String> values);
}
