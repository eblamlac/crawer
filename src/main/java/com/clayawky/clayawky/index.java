package com.clayawky.clayawky;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
/**
 * 爷的拖拽！
 * **/
class DragListener implements EventHandler<MouseEvent> {

    private double x = 0;
    private double y = 0;
    private  double x_stage=0;
    private  double y_stage=0;
    private final Stage stage;

    public DragListener(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {

        event.consume();
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            //获取当用户点击时候event的位置
            System.out.println("哈哈");
            x=event.getScreenX();
            y =event.getScreenY();
            x_stage = stage.getX();
            y_stage = stage.getY();
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            stage.setX(x_stage + event.getScreenX() - x);
            stage.setY(y_stage + event.getScreenY() - y);
        }

    }

    public void enableDrag(Node node) {
        node.setOnMousePressed(this);
        node.setOnMouseDragged(this);
    }
}


/**
 * 主页面
 * **/
public class index extends Application {
    File file = new File("C:\\Users\\ClayAwky\\Desktop\\爬虫\\clayawky\\src\\main\\resources\\com\\clayawky\\clayawky\\gaga.png");
    String localUrl = file.toURI().toURL().toString();
    Text topText=null;
    String userAddress;
    String url=null;
    StringBuffer address=new StringBuffer();
   static VBox logger =new VBox();
    ScrollPane log=new ScrollPane();
    BorderPane root = new BorderPane();
    VBox top=new VBox();
    public static String nani=null;

    public index() throws MalformedURLException {
    }

    @Override
    public void start(Stage stage) throws IOException {
        Text rizhi=new Text(20,20,"日志：");
        rizhi.setStyle("-fx-font-weight: 700;"+"-fx-font-size: 15");
        logger.getChildren().addAll(rizhi);
        Rectangle rect = new Rectangle(20,20,400,25);
        rect.setAccessibleText("nani");
      topText=new Text("选择下载目录？");
      topText.setWrappingWidth(400);
    topText.setTextAlignment(TextAlignment.CENTER);
        StackPane stack = new StackPane();
        stack.setOnMouseClicked(event->{
            System.out.println("aa");
            DirectoryChooser chooser=new DirectoryChooser();
            chooser.setInitialDirectory(new File("C:\\Users"));  ;
            chooser.setTitle("选择下载目录");
            File file= chooser.showDialog(stage);
           if(file!=null) {
               if (file.isDirectory()) {

                   if(topText.toString().length()>20){
                       String nani =file.toString();
                       userAddress=nani;
                    for (String a :nani.split("\\\\")) {
                        address.append(a).append("/");
                        if(address.length()>25){
                            address.append("...");
                            break;
                        }
                    }
                   }else {
                       address.append(file.toString());
                   }
                   topText =new Text(address.toString());
                   topText.setStyle("-fx-max-width: 400");
                   topText.setWrappingWidth(400);
                   topText.setTextAlignment(TextAlignment.CENTER);
                   stack.getChildren().clear();
                   stack.getChildren().addAll(rect,topText);
                   topText.setStyle("-fx-max-width: 400");
                   topText.setWrappingWidth(200);
               }
           }
        });

        stack.setAlignment(Pos.CENTER);
        stack.setStyle("-fx-cursor: hand");
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setFill(Color.GRAY);
        rect.setStroke(Color.GRAY);
        stack.getChildren().addAll(rect,topText);
        stack.setPrefWidth(400);
        stack.setMaxWidth(400);

       HBox btn1= new HBox();
       btn1.setAlignment(Pos.CENTER);
       btn1.setPadding(new Insets(20));
        Button btn=new Button();
        btn.setStyle(" -fx-background-color: linear-gradient(to right,#9370DB,#7B68EE);"+"\n" +
                "    -fx-background-radius: 25;"+"-fx-text-fill: white;"+"-fx-cursor: hand");
        HBox textfile= new HBox();
        textfile.setAlignment(Pos.CENTER);
        TextField text= new TextField("请输入目标用户的链接");
             text.setStyle("-fx-pref-width:500;"+"-fx-background-radius:20");
              text.setOnKeyReleased(t -> {
              url=text.getText().trim().toString();
              });
        textfile.getChildren().add(text);
        btn1.getChildren().addAll(textfile,btn);

        BackgroundFill blue =new BackgroundFill(Color.GRAY,null,null);
        text.setBackground(new Background(blue));
        btn.setText("开始下载数据!");

        EventHandler<ActionEvent> goAction = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                    System.out.println(event);
            }
        };
       btn.setOnAction(MouseEvent->{
         System.out.println(url);
         System.out.println(userAddress);
          if(userAddress==null||userAddress.equals("")){
               Text noUrl=new Text(20,20,"请选择安装目录");
               noUrl.setStyle("-fx-font-weight: 700;"+"-fx-font-size: 15");
               noUrl.setFill(Color.RED);
               logger.getChildren().addAll(noUrl);
               return;

           }else if (url==null||url.equals("")){
               Text noAddress=new Text(20,20,"请输入用户主页地址");
               noAddress.setStyle("-fx-font-weight: 700;"+"-fx-font-size: 15");
               noAddress.setFill(Color.RED);
               logger.getChildren().addAll(noAddress);
               return;
           }
           if((userAddress!=null)&&(url!=null)) {
               System.out.println("开始下载");

               crawler.GithubRepoPageProcessor1(url);
               Text page=new Text(20,20, crawler.getData() );
               page.setStyle("-fx-font-weight: 700;"+"-fx-font-size: 15");
               page.setFill(Color.BLUE);
               logger.getChildren().addAll(page);
               return;

           }
           MouseEvent.consume();
       });

        stage.getIcons().add(new Image(
                index.class.getResourceAsStream("gaga.png")));
        stage.setTitle("ClayAwky");


        logger.setPadding(new Insets(20));
        logger.setStyle(
                "-fx-pref-width: 100%;"+"-fx-pref-height: 200;");

        log.setContent(logger);
        log.setOpacity(0.7);
        VBox BottomLog=new VBox();
        Button clear =new Button();
        clear.setText("清空日志？");

        clear.setStyle("-fx-cursor: hand;"+"-fx-background-color: linear-gradient(to right,#9370DB,#7B68EE);"+"-fx-font-weight: 700;");
        clear.setTextFill(Color.WHITE);

        clear.setOnAction(event -> {
            logger.setPadding(new Insets(20));
            logger.setStyle(
                    "-fx-pref-width: 100%;"+"-fx-pref-height: 200;");
            Text rizhi1=new Text(20,20,"日志：");
            rizhi1.setStyle("-fx-font-weight: 700;"+"-fx-font-size: 15");
            logger.getChildren().clear();
            logger.getChildren().add(rizhi1);
            log.setContent(logger);
        });



        BottomLog.setSpacing(10);
        BottomLog.setAlignment(Pos.TOP_RIGHT);
        BottomLog.getChildren().addAll(clear,log);
        HBox choose =new HBox();
        Button douyin =new Button("抖音？");
        Button kuaishou =new Button("快手？");

        kuaishou.setStyle("-fx-background-color: yellow;"+"-fx-text-fill: black;"+"-fx-cursor: hand;"+"-fx-pref-width: 100;"+"-fx-pref-height: 30;"+"-fx-font-weight: 700;");
        douyin.setStyle("-fx-background-color: indigo;"+"-fx-text-fill: white;"+"-fx-cursor: hand;"+"-fx-pref-width: 100;"+"-fx-pref-height: 30;"+"-fx-font-weight: 700;");
        kuaishou.setOnAction(event->{
            kuaishou.setStyle("-fx-background-color: yellow;"+"-fx-text-fill: black;"+"-fx-cursor: hand;"+"-fx-border-color: blue;"+"-fx-pref-width: 100;"+"-fx-pref-height: 30;"+"-fx-font-weight: 700;");
            douyin.setStyle("-fx-background-color: indigo;"+"-fx-text-fill: white;"+"-fx-cursor: hand;"+"-fx-pref-width: 100;"+"-fx-pref-height: 30;"+"-fx-font-weight: 700;");
        });
        douyin.setOnAction(event->{
            kuaishou.setStyle("-fx-background-color: yellow;"+"-fx-text-fill: black;"+"-fx-cursor: hand;"+"-fx-pref-width: 100;"+"-fx-pref-height: 30;"+"-fx-font-weight: 700;");
            douyin.setStyle("-fx-background-color: indigo;"+"-fx-text-fill: white;"+"-fx-cursor: hand;"+"-fx-border-color: blue;"+"-fx-pref-width: 100;"+"-fx-pref-height: 30;"+"-fx-font-weight: 700;");
        });
        choose.getChildren().addAll(douyin,kuaishou);
        choose.setSpacing(50);
        choose.setAlignment(Pos.CENTER);
        Text chose =new Text("选择不同的平台？");
        chose.setStyle("-fx-font-weight: 700;"+"-fx-font-size: 20;");
        chose.setFill(Color.WHITE);
       VBox vt=new VBox();
       vt.setSpacing(20);
       vt.getChildren().addAll(chose,choose);
       vt.setAlignment(Pos.CENTER);
  ;
//     root.setStyle(new StringBuilder()
//             .append("-fx-background-image: url(")
//             .append("file:src/main/resources/com/clayawky/clayawky/556.jpg")
//             .append("); ").append("-fx-background-position: center center; ")
//             .append("-fx-background-repeat: stretch;")
//             .append("-fx-background-color:  transparent;")
//             .append("-fx-background-size: cover;")
//             .toString());
     VBox topMax=new VBox();
        GridPane toplan= new GridPane();
        Button minButton = new Button("—");
        Button amxButton = new Button("口");
        Button closeButton = new Button("X");
        minButton.setStyle("-fx-background-color: transparent;  -fx-cursor:hand;"
                + "-fx-max-height: infinity;-fx-text-fill:rgb(177,177,177) ; -fx-border-image-insets: 0;");
        amxButton.setStyle("-fx-background-color: transparent;-fx-cursor:hand;"
                + "-fx-max-height: infinity;-fx-text-fill: rgb(177,177,177) ; -fx-border-image-insets: 0;");
        closeButton.setStyle("-fx-background-color: transparent; -fx-cursor:hand;"
                + "-fx-max-height: infinity;-fx-text-fill:rgb(177,177,177) ; -fx-border-image-insets: 0;");
        minButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.setIconified(true);
            }
        });
        amxButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.setMaximized(!stage.isMaximized());
            }
        });
        closeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               stage.close();
            }
        });
        Label label = new Label("ClayAwky");
        label.setFont(Font.font(14));
        label.setTextFill(Paint.valueOf("white"));
        ImageView imageView = new ImageView(localUrl);
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        label.setGraphic(imageView);
        toplan.setStyle("-fx-background-color: rgb(60,60,60);");
       toplan.addColumn(0, label);
        GridPane.setHgrow(label, Priority.ALWAYS);
        toplan.addColumn(1, minButton);
        toplan.addColumn(2, amxButton);
        toplan.addColumn(3, closeButton);

        toplan.setOnMouseDragged(event -> {
            new DragListener(stage).enableDrag(toplan);
        });
        toplan.setOnMousePressed(event -> {
            new DragListener(stage).enableDrag(toplan);
        });
     stage.initStyle(StageStyle.TRANSPARENT);
        root.setStyle(new StringBuilder()
                .append("-fx-background-image: url(")
                .append("file:src/main/resources/com/clayawky/clayawky/556.jpg")
                .append("); ").append("-fx-background-position: center center; ")
                .append("-fx-background-repeat: stretch;")
                .append("-fx-background-color:  transparent;")
                .append("-fx-background-size: cover;")
                .toString());
        top.getChildren().addAll(toplan,stack,btn1);
        top.setAlignment(Pos.CENTER);
        root.setCenter(vt);
        root.setBottom(BottomLog);
        root.setTop(top);
        stage.setScene(new Scene(root,1000,500));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}