## TableBuilder

Display a list as table in console for debug something

### Test

Console font Monospaced and size require be even number

#### Display Ansi

```java
public class TableBuilderTest {
    @Test
    public void display() {
        TableBuilder.newBuilder()
                .enableAnsi()
                .enableAutoIndex()
                .setHeadBackground(Ansi.Color.CYAN)
                .setBodyBackgroundOdd(Ansi.Color.YELLOW)
                .setBodyBackgroundEven(Ansi.Color.WHITE)
                .display(data);
    }
}
```

### Console

<img src="https://danielpine.github.io/img/ansi-console.png" width = "100%"/>

#### Display normal

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

## Maven repos

```xml

<repository>
    <id>maven2</id>
    <url>http://repo1.maven.apache.org/maven2/</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository> 
```

```xml

<repository>
    <id>sonatype</id>
    <url>https://s01.oss.sonatype.org/content/groups/public</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository> 
```

```xml

<repository>
    <id>aliyun</id>
    <url>https://maven.aliyun.com/repository/central</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository> 
```

```xml

<dependency>
    <groupId>io.github.danielpine</groupId>
    <artifactId>boring-tools</artifactId>
    <version>1.0.2-RELEASE</version>
</dependency>
```
