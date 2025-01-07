package com.example.inf04_edu_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private static final int CREATE_ITEM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        loadSampleItems();

        adapter = new ItemAdapter(itemList, this::onItemClick);
        recyclerView.setAdapter(adapter);
    }

    private void loadSampleItems() {


        /* ------ ListView ------ */


        Item item1 = new Item("ListView", "ListView służy do wyświetlanie informacji w liście");

        /* Android */
        item1.addSection(new Section(Section.Type.H1, "ListView w Android"));
        List<String> subpoints = new ArrayList<>();
        subpoints.add("Szukanie ListView stworzonego w kreatorze");
        subpoints.add("Definicja i inicjalizacja tablicy z elementami");
        subpoints.add("Definicja i inicjalizacja adaptera");
        subpoints.add("Ustawienie adaptera");
        subpoints.add("Ustawienie eventu na kliknięcie elementu");
        item1.addSection(new Section(Section.Type.LIST, "ListView w Android", 1));
        item1.addListSection(subpoints);
        item1.addSection(new Section(Section.Type.H4, "Szukanie ListView stworzonego w kreatorze"));
        item1.addSection(new Section(Section.Type.CODE, "listview = findViewById(R.id.listView);"));
        item1.addSection(new Section(Section.Type.H4, "Definicja i inicjalizacja tablicy z elementami"));
        item1.addSection(new Section(Section.Type.TEXT, ("Tutaj zrobione statycznie")));
        item1.addSection(new Section(Section.Type.CODE, "String[] listData = {\"Element 1\", \"Element 2\", \"Element 3\"};"));
        item1.addSection(new Section(Section.Type.H4, ("Definicja i inicjalizacja adaptera")));
        item1.addSection(new Section(Section.Type.CODE, "" +
                "ArrayAdapter<String> adapter = new ArrayAdapter<>(\n" +
                "   this, /*context - obiekt(Activity) w którym ArrayAdapter ma szukać zasobów Views, Arrays, Resources itd.*/\n" +
                "   android.R.layout.simple_list_item_1, /*resource - wybór układu*/\n" +
                "   listData /*tablica z elementami*/\n" +
                ");"));
        item1.addSection(new Section(Section.Type.H4, ("Ustawienie adaptera")));
        item1.addSection(new Section(Section.Type.CODE, ("listview.setAdapter(adapter);")));
        item1.addSection(new Section(Section.Type.H4, ("Ustawienie adaptera")));
        item1.addSection(new Section(Section.Type.CODE, "" +
                "listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                "   @Override\n" +
                "   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n" +
                "       /* event */\n" +
                "   }\n" +
                "});"));
        item1.addSection(new Section(Section.Type.H4, ("Cały kod")));
        item1.addSection(new Section(Section.Type.CODE, (
                "/* importy, po najechaniu na czerwony element kliknij Import Class */\n" +
                "\n" +
                "\n" +
                "public class MainActivity extends AppCompatActivity {\n" +
                "    private ListView listview;\n" +
                "\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        EdgeToEdge.enable(this);\n" +
                "        setContentView(R.layout.activity_main);\n" +
                "        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {\n" +
                "            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());\n" +
                "            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);\n" +
                "            return insets;\n" +
                "        });\n" +
                "\n" +
                "        listview = findViewById(R.id.listView);\n" +
                "        String[] listData = {\"Element 1\", \"Element 2\", \"Element 3\"};\n" +
                "        ArrayAdapter<String> adapter = new ArrayAdapter<>(\n" +
                "                this, /* context - obiekt(Activity) w którym ArrayAdapter ma szukać zasobów Views, Arrays, Resources itd. */\n" +
                "                android.R.layout.simple_list_item_1, /* resource - wybór układu */\n" +
                "                listData /* tablica z elementami */\n" +
                "        );\n" +
                "        listview.setAdapter(adapter);\n" +
                "\n" +
                "        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                "            @Override\n" +
                "            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n" +
                "                Toast toast = new Toast(getBaseContext());\n" +
                "                toast.setText(((TextView) view).getText());\n" +
                "                toast.setDuration(Toast.LENGTH_SHORT);\n" +
                "                toast.show();\n" +
                "            }\n" +
                "        });\n" +
                "    }\n" +
                "}"
        )));
        item1.addSection(new Section(Section.Type.H4, "Wizualizacja"));
        item1.addSection(new Section(Section.Type.EXAMPLE, "ListView"));

        /* JavaFX */

        item1.addSection(new Section(Section.Type.H1, "ListView w JavaFX"));
        subpoints = new ArrayList<>();
        subpoints.add("implements Initializable lub extends Application");
        subpoints.add("Deklaracja");
        subpoints.add("Ustawianie trybu zaznaczania Single lub Multiple");
        subpoints.add("Wypełnianie tablicą lub listą");
        subpoints.add("Dodawanie pojedynczego elementu");
        subpoints.add("Pobieranie zaznaczonego elementu przy użyciu przycisku wywołującego metodę");
        item1.addSection(new Section(Section.Type.LIST, "ListView w Adnroid"));
        item1.addListSection(subpoints);
        item1.addSection(new Section(Section.Type.H4, "implements Initializable lub extends Application"));
        item1.addSection(new Section(Section.Type.CODE, "" +
                "public class HelloController extends Application {\n" +
                "   @Override\n" +
                "   public void start(Stage stage) throws Exception {\n" +
                "      /* kod, który ma się wywołać przy załadowaniu widoku */\n" +
                "   }\n" +
                "}"
        ));
        item1.addSection(new Section(Section.Type.H6, "lub"));
        item1.addSection(new Section(Section.Type.CODE,"" +
                "public class HelloController implements Initializable {\n" +
                "   @Override\n" +
                "   public void initialize(URL url, ResourceBundle resourceBundle) {\n" +
                "      /* kod, który ma się wywołać przy załadowaniu widoku */\n" +
                "   }\n" +
                "}"
        ));
        item1.addSection(new Section(Section.Type.H4, "Deklaracja"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   @FXML /* bez tego program nie znajdzie naszego elementu (tu listy) */ \n" +
                "   private ListView listView; /* WAŻNE! W JavaFX nazwa zmiennej musi być taka sama, jak ta nadana w SceneBuilderze lub pliku XML */"
        )));
        item1.addSection(new Section(Section.Type.H4, "Ustawianie trybu zaznaczania Single lub Multiple"));
        item1.addSection(new Section(Section.Type.H6, "Tryb Single"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);"
                )));
        item1.addSection(new Section(Section.Type.H6, "Tryb Multiple"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);"
        )));
        item1.addSection(new Section(Section.Type.H4, "Wypełnianie tablicą lub listą"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   listView.getItems().addAll(\n" +
                "       new String[]{\"item1\", \"item2\", \"item3\"}\n" +
                "   );"
        )));
        item1.addSection(new Section(Section.Type.H6, "lub"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   ArrayList<String> list = new ArrayList<>();\n" +
                "   list.add(\"item1\");\n" +
                "   list.add(\"item2\");\n" +
                "   list.add(\"item3\");\n" +
                "   listView.getItems().addAll(list);")));
        item1.addSection(new Section(Section.Type.H6, "lub"));
        item1.addSection(new Section(Section.Type.CODE, (
                "// Tworzenie ObservableList i dodawanie danych\n" +
                "   ObservableList<String> items = FXCollections.observableArrayList(\n" +
                "       \"Element 1\",\n" +
                "       \"Element 2\",\n" +
                "       \"Element 3\",\n" +
                "       \"Element 4\",\n" +
                "       \"Element 5\"\n" +
                "   );\n" +
                "\n" +
                "   // Powiązanie danych z ListView\n" +
                "   listView.setItems(items);\n" +
                "\n" +
                "   // Obsługa zdarzenia kliknięcia na element ListView\n" +
                "   listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "       System.out.println(\"Wybrano: \" + newValue);\n" +
                "   });"
        )));
        item1.addSection(new Section(Section.Type.H4, "Dodawanie pojedynczych elementów"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   listView.getItems().add(\"item1\");\n" +
                "   listView.getItems().add(\"item2\");\n" +
                "   listView.getItems().add(\"item3\");"
        )));
        item1.addSection(new Section(Section.Type.H4, "Pobieranie zaznaczonego elementu przy użyciu przycisku wywołującego metodę"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   public void getActiveElement(ActionEvent actionEvent) {\n" +
                "       System.out.println(\n" +
                "           listView.getSelectionModel().getSelectedItem() /* pobieranie zaznaczonego elementu */\n" +
                "               .toString()\n" +
                "       );\n" +
                "   }"
        )));
        item1.addSection(new Section(Section.Type.H4, "Pobieranie zaznaczonego elementu przy kliknięciu"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "       System.out.println(\"Wybrano: \" + newValue);\n" +
                "   });"
        )));
        item1.addSection(new Section(Section.Type.H4, "Czyszczenie ListView"));
        item1.addSection(new Section(Section.Type.CODE, (
                "   listView.getItems().clear();"
        )));
        item1.addSection(new Section(Section.Type.H4, ("Cały kod")));
        item1.addSection(new Section(Section.Type.CODE, (
                "/* importy, po najechaniu na czerwony element kliknij Import Class */\n" +
                "\n" +
                "\n" +
                "public class HelloController implements Initializable {\n" +
                "\n" +
                "    @FXML\n" +
                "    private ListView listView;\n" +
                "    @FXML\n" +
                "    private ListView listView2;\n" +
                "    @FXML\n" +
                "    private ListView listView3;\n" +
                "\n" +
                "    @Override\n" +
                "    public void initialize(URL url, ResourceBundle resourceBundle) {\n" +
                "        listView.getItems().addAll(new String[]{\"item1\", \"item2\", \"item3\"});\n" +
                "\n" +
                "        ArrayList<String> list = new ArrayList<>();\n" +
                "        list.add(\"item1\");\n" +
                "        list.add(\"item2\");\n" +
                "        list.add(\"item3\");\n" +
                "        listView2.getItems().addAll(list);\n" +
                "\n" +
                "        listView3.getItems().add(\"item1\");\n" +
                "        listView3.getItems().add(\"item2\");\n" +
                "        listView3.getItems().add(\"item3\");\n" +
                "\n" +
                "        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "            Notifications.create()\n" +
                "                    .title(\"listView\")\n" +
                "                    .text((String) newValue)\n" +
                "                    .hideAfter(Duration.seconds(3))\n" +
                "                    .position(Pos.BOTTOM_RIGHT)\n" +
                "                    .show();\n" +
                "        });\n" +
                "\n" +
                "        listView2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "            Notifications.create()\n" +
                "                    .title(\"listView2\")\n" +
                "                    .text((String) newValue)\n" +
                "                    .hideAfter(Duration.seconds(3))\n" +
                "                    .position(Pos.BOTTOM_RIGHT)\n" +
                "                    .show();\n" +
                "        });\n" +
                "\n" +
                "        listView3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "            Notifications.create()\n" +
                "                    .title(\"listView3\")\n" +
                "                    .text((String) newValue)\n" +
                "                    .hideAfter(Duration.seconds(3))\n" +
                "                    .position(Pos.BOTTOM_RIGHT)\n" +
                "                    .show();\n" +
                "        });\n" +
                "\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public void clearLists(ActionEvent actionEvent) {\n" +
                "        listView.getItems().clear();\n" +
                "        listView2.getItems().clear();\n" +
                "        listView3.getItems().clear();\n" +
                "    }\n" +
                "\n" +
                "    public void getActiveElement(ActionEvent actionEvent) {\n" +
                "        if (listView.getSelectionModel().getSelectedItem() == null ||\n" +
                "                listView2.getSelectionModel().getSelectedItem() == null ||\n" +
                "                listView3.getSelectionModel().getSelectedItem() ==null) {\n" +
                "            Alert alert = new Alert(Alert.AlertType.ERROR);\n" +
                "            alert.setTitle(\"Error\");\n" +
                "            alert.setContentText(\"Nie zaznaczono elementu w każdym ListView\");\n" +
                "            alert.show();\n" +
                "            return;\n" +
                "        }\n" +
                "        Alert alert = new Alert(Alert.AlertType.INFORMATION);\n" +
                "        alert.setTitle(\"Button Clicked\");\n" +
                "        alert.setContentText(\n" +
                "                listView.getSelectionModel().getSelectedItem().toString() + \"\\n\" +\n" +
                "                listView2.getSelectionModel().getSelectedItem().toString() + \"\\n\" +\n" +
                "                listView3.getSelectionModel().getSelectedItem().toString() + \"\\n\"\n" +
                "                );\n" +
                "        alert.show();\n" +
                "    }\n" +
                "}"
        )));


        /* ------ Spinner/ComboBox ------ */


        Item item2 = new Item("Spinner/ComboBox", "Spinner/ComboBox służy do wyświetlania listy z możliwością wyborania jednego z elementów.");

        /* Android */
        subpoints = new ArrayList<>();
        subpoints.add("Inicializacja");
        subpoints.add("Wypełanianie");
        subpoints.add("Pobieranie danych");
        subpoints.add("Event po kliknięciu elementu");
        item2.addListSection(subpoints);
        item2.addSection(new Section(Section.Type.H4, subpoints.get(0)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   @FXML\n" +
                "   public ComboBox<String> comboBox;"
        )));
        item2.addSection(new Section(Section.Type.H4, subpoints.get(1)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   comboBox.getItems().setAll(new String[] {\"koszykówka\", \"ping pong\", \"siatkówka\"});"
        )));
        item2.addSection(new Section(Section.Type.H4, subpoints.get(2)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "       System.out.println(\"Wybrano \" + newValue);\n" +
                "   });"
        )));
        item2.addSection(new Section(Section.Type.H4, subpoints.get(3)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   comboBox.getSelectionModel().getSelectedItem()"
        )));
        item2.addSection(new Section(Section.Type.H4, "Cay kod"));
        item2.addSection(new Section(Section.Type.CODE, (
                "Spinner spinner = new Spinner(this);\n" +
                "String[] spinnerData = {\"Opcja 1\", \"Opcja 2\", \"Opcja 3\"};\n" +
                "ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);\n" +
                "spinner.setAdapter(spinnerAdapter);"
        )));
        item2.addSection(new Section(Section.Type.EXAMPLE, "Spinner"));

        /* JavFX */
        item2.addSection(new Section(Section.Type.LIST, null));
        subpoints = new ArrayList<>();
        subpoints.add("Inicializacja");
        subpoints.add("Wypełanianie");
        subpoints.add("Pobieranie danych");
        subpoints.add("Event po kliknięciu elementu");
        item2.addListSection(subpoints);
        item2.addSection(new Section(Section.Type.H4, subpoints.get(0)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   @FXML\n" +
                "   public ComboBox<String> comboBox;"
        )));
        item2.addSection(new Section(Section.Type.H4, subpoints.get(1)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   comboBox.getItems().setAll(new String[] {\"koszykówka\", \"ping pong\", \"siatkówka\"});"
        )));
        item2.addSection(new Section(Section.Type.H4, subpoints.get(2)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "       System.out.println(\"Wybrano \" + newValue);\n" +
                "   });"
        )));
        item2.addSection(new Section(Section.Type.H4, subpoints.get(3)));
        item2.addSection(new Section(Section.Type.CODE, (
                "   comboBox.getSelectionModel().getSelectedItem()"
        )));
        item2.addSection(new Section(Section.Type.H4, "Cay kod"));
        item2.addSection(new Section(Section.Type.CODE, (
                "/* importy, po najechaniu na czerwony element kliknij Import Class */\n" +
                "public class ComboBoxController implements Initializable {\n" +
                "    @FXML\n" +
                "    public ComboBox<String> comboBox;\n" +
                "\n" +
                "    @Override\n" +
                "    public void initialize(URL url, ResourceBundle resourceBundle) {\n" +
                "        comboBox.getItems().setAll(new String[] {\"koszykówka\", \"ping pong\", \"siatkówka\"});\n" +
                "        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {\n" +
                "            System.out.println(\"Wybrano \" + newValue);\n" +
                "        });\n" +
                "    }\n" +
                "}\n"
        )));

        /* ------ Regex ------ */

        Item item3 = new Item("*Regex", "Regex służy do znajdywania ciągów znaków.");
        item3.addSection(new Section(Section.Type.LIST, null)); // Pusty content dla LIST
        List<String> items = new ArrayList<>();
        items.add("\\\\b - Granica słowa, aby dopasowywać całe słowa.");
        items.add("[A-Z] - Pierwszy znak to wielka litera.");
        items.add("[a-z]* - Dowolna liczba małych liter (lub brak).");
        items.add("([A-Z][a-z]*)? - Opcjonalna kolejna wielka litera, po której mogą wystąpić małe litery.\n" +
                    "   Nawias () oznacza grupę.\n" +
                    "   Znak ? wskazuje, że ta grupa może wystąpić 0 lub 1 raz.");
        items.add("\\\\b - Granica słowa na końcu.");
        item3.addListSection(items);

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
    }

    private void onItemClick(Item item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ITEM", item);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_ITEM_REQUEST && resultCode == RESULT_OK && data != null) {
            Item newItem = (Item) data.getSerializableExtra("NEW_ITEM");
            if (newItem != null) {
                itemList.add(newItem);
                adapter.notifyItemInserted(itemList.size() - 1);
            }
        }
    }
}
