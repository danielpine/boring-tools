## TableBuilder

Display a list as table in console for debug something

### Test

```java
public class TableBuilderTest {
  @Test
  public void display() {
    TableBuilder.newBuilder().display(
            Arrays.asList(
                    new User("小雨点", "Rain", "China"),
                    new User("Daniel Pine", "Pine", "中国")
            )
    );
  }
}
```
### Console
<img src="https://danielpine.github.io/img/page-console.png" width = "100%"/>

## Maven repo
https://s01.oss.sonatype.org/content/groups/public
