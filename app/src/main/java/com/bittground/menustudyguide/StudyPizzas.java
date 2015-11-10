package com.bittground.menustudyguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class StudyPizzas extends AppCompatActivity {
    private Toast addIngredientToast;
    private Toast removeIngredientToast;
    private String[] Pizzas = {"Thai Pie", "Tomato/Basil", "Athenian", "MacKenzie River", "BLT","Flathead", "BBQ", "Stockman", "Caribbean Chicken", "Bear Tooth Sausage", "Rancher", "Madison","Sequoia", "Veggie", "Buffalo Wing", "Branding Iron", "Cottonwood", "Hot Hawaiian", "Good Ol' Boy","Bell Ranch","Philly Cheesesteak","Bistro"};
    private ArrayList<String> chosenIngredients = new ArrayList<String>();
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private int pizzaNumber = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_study_pizzas);
        addIngredientToast = Toast.makeText(this, " ", Toast.LENGTH_SHORT);
        removeIngredientToast = Toast.makeText(this, " ", Toast.LENGTH_SHORT);
        setPizzaToStudy();
    }


    private void setPizzaToStudy() {
        TextView pizzaTextView = (TextView) findViewById(R.id.menu_title);
        if (pizzaNumber == -1) {
            pizzaNumber = (int) (Math.random() * 22);
        } else if (pizzaNumber < Pizzas.length - 1) {
            pizzaNumber += 1;
        } else
            pizzaNumber = 0;


        String thePizza = Pizzas[pizzaNumber];
        pizzaTextView.setText(thePizza + " Pizza");
    }

    public void toggleSelected(View view) {
        Button button = (Button) view;
        String ingredient = button.getText().toString();
        if (!button.isSelected()) {
            button.setSelected(true);
            button.setAlpha(1f);
            buttons.add(button);
            chosenIngredients.add(ingredient);
            removeIngredientToast.cancel();
            addIngredientToast.cancel();
            addIngredientToast = Toast.makeText(this, ingredient + " Added", Toast.LENGTH_SHORT);
            addIngredientToast.show();
        } else {
            button.setSelected(false);
            button.setAlpha(.4f);
            buttons.remove(button);
            chosenIngredients.remove(ingredient);
            addIngredientToast.cancel();
            removeIngredientToast.cancel();
            removeIngredientToast = Toast.makeText(this, ingredient + " Removed", Toast.LENGTH_SHORT);
            removeIngredientToast.show();
        }


    }

    private void correct(String[] ingredients) {
        //show correct message and go to next pizza
        AlertDialog.Builder correctDialog = new AlertDialog.Builder(StudyPizzas.this);
        correctDialog.setTitle("Correct!");
        correctDialog.setIcon(R.drawable.sausage);
        String correctIngredients = "";
        for(int i = 0; i < ingredients.length; i++){
            correctIngredients = correctIngredients + ", " +ingredients[i];
        }
        correctDialog.setMessage("The " + ingredients.length + " ingredients were" + correctIngredients+ "." + " Would you like to continue studying?");

        correctDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                StudyPizzas.this.finish();
            }
        });
        correctDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //set all ingredients back to false

                        //unselect all buttons
                        for (int i = 0; i < buttons.size(); i++) {
                            buttons.get(i).setSelected(false);
                            buttons.get(i).setAlpha(.7f);
                        }
                        buttons.clear();
                        chosenIngredients.clear();

                        Toast toast = Toast.makeText(StudyPizzas.this, "Good Luck!", Toast.LENGTH_SHORT);
                        toast.show();
                        setPizzaToStudy();
                        ScrollView mainScrollView = (ScrollView) findViewById(R.id.main_scroll_view);
                        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
                        dialog.cancel();
                    }
                });
        correctDialog.show();
    }

    private void wrong(String[] ingredients) {
        AlertDialog.Builder incorrectDialog = new AlertDialog.Builder(StudyPizzas.this);
        incorrectDialog.setTitle("Sorry That Is Wrong");
        String correctIngredients = "";
        for(int i = 0; i < ingredients.length; i++){
            correctIngredients = correctIngredients + ", " +ingredients[i];
        }
        incorrectDialog.setMessage("The ingredients were" + correctIngredients+ ". Try Again?");

        incorrectDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                StudyPizzas.this.finish();
            }
        });
        incorrectDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //set all ingredients back to false
                        //unselect all buttons
                        for (int i = 0; i < buttons.size(); i++) {
                            buttons.get(i).setSelected(false);
                        }
                        buttons.clear();
                        chosenIngredients.clear();
                        Toast toast = Toast.makeText(StudyPizzas.this, "Try Again!", Toast.LENGTH_SHORT);
                        toast.show();
                        ScrollView mainScrollView = (ScrollView) findViewById(R.id.main_scroll_view);
                        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
                        dialog.cancel();
                    }
                });
        incorrectDialog.show();
    }

    public void checkPizza(View view) {
        int w = 0;
        String[] ingredients;
        //set each pizza ingredient to opposit and then check if any are still true and if they are then the ingredients are wrong.
        switch (pizzaNumber) {
            case 0:
                ingredients = new String[]{"Peanut/Ginger", "Chicken", "Peanuts", "Red Peppers", "Green Onions", "Oranges", "Mozzarella", "Cilantro"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 1:
                ingredients = new String[]{"Pizza/Garlic", "Sliced Tomatoes", "Basil","Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 2:
                ingredients = new String[]{"Oil/Garlic", "Spinach", "Sliced Tomatoes", "Red Onions", "Kalamata Olives", "Mozzarella", "Feta"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 3:
                ingredients = new String[]{"Oil/Garlic","Spinach","Roasted Zucchini","Diced Tomatoes", "Mushrooms","Mozzarella","Feta"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 4:
                ingredients = new String[]{"Ranch", "Mozzarella","Bacon","Shredded Lettuce","Diced Tomatoes"};
            if (chosenIngredients.size() == ingredients.length) {
                ArrayList<String> pizzaIngredients = new ArrayList<>();
                pizzaIngredients.addAll(Arrays.asList(ingredients));
                if (chosenIngredients.containsAll(pizzaIngredients)) {
                    correct(ingredients);
                } else {
                    wrong(ingredients);
                }
            } else {
                Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                toast.show();
            }
            break;
            case 5:
                ingredients = new String[]{"Alfredo", "Spinach","Chicken","Bacon","Diced Tomatoes", "Mushrooms", "Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 6:
                ingredients = new String[]{"BBQ","BBQ Chicken or Pork", "Red Onions","Cilantro","Mozzarella","Cheddar"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 7:
                ingredients = new String[]{"Pizza","Pepperoni","Sausage","Bacon","Steak","Mozzarella","Cheddar"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 8:
                ingredients = new String[]{"Caribbean", "Chicken", "Red Peppers", "Pineapple", "Mozzarella", "Cilantro"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 9:
                ingredients = new String[]{"Pizza","Sausage","Red Onions","Diced Tomatoes","Red Peppers", "Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 10:
                ingredients = new String[]{"Pizza","Pepperoni","Beef Crumbles", "Bacon", "Sliced Tomatoes", "Green Peppers", "Red Onions", "Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 11:
                ingredients = new String[]{"Pizza","Bacon","Mushrooms","Ricotta Cheese Dollops", "Mozzarella", "Cheddar"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 12:
                ingredients = new String[]{"Pesto","Sundried Tomatoes","Artichoke Hearts", "Toasted Pine Nuts", "Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 13:
                ingredients = new String[]{"Pesto", "Red Onions", "Diced Tomatoes","Mushrooms","Roasted Zucchini","Broccoli","Mozzarella", "Parmesan"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 14:
                ingredients = new String[]{"Buffalo", "Chicken","Celery","Bleu Cheese","Buffalo Wing Sauce","Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 15:
                ingredients = new String[]{"Pizza","Sausage","Jalapenos","Crushed Red Pepper Flakes","Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 16:
                ingredients = new String[]{"Pizza","Chicken","Diced Tomatoes","Mushrooms","Basil","Roasted Garlic","Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 17:
                ingredients = new String[]{"BBQ","Chicken","Bacon","Pineapple","Jalapenos","Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 18:
                ingredients = new String[]{"Pizza","Mozzarella","Pepperoni"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 19:
                ingredients = new String[]{"BBQ","Chicken","Black Beans","Red Onions","Mozzarella"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 20:
                ingredients = new String[]{"Alfredo","Steak", "Mushrooms","Red Onions","Green Peppers","Provolone", "Sliced Pepperoncini"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 21:
                ingredients = new String[]{"Oil/Garlic","Pear Slices","Mozzarella","Bleu Cheese","Prosciutto","Balsamic Glaze"};
                if (chosenIngredients.size() == ingredients.length) {
                    ArrayList<String> pizzaIngredients = new ArrayList<>();
                    pizzaIngredients.addAll(Arrays.asList(ingredients));
                    if (chosenIngredients.containsAll(pizzaIngredients)) {
                        correct(ingredients);
                    } else {
                        wrong(ingredients);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "There should be " + ingredients.length +" ingredients", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }

        /*
        final boolean[] ingredientList = {mozzerrella, gouda, fontina, pizza, pizzagarlic, bbq, buffalo, ranch, pesto, oilgarlic, alfredo, carribbean, peanutginger,};

        for (boolean anIngredientList : ingredientList) {
            if (anIngredientList) {
                w = w + 1;
            }
        }

        if (w == 0) {
            //show correct message and go to next pizza
            AlertDialog.Builder correctDialog = new AlertDialog.Builder(StudyPizzas.this);
            correctDialog.setTitle("Correct!");
            correctDialog.setMessage("Would you like to continue studying?");

            correctDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    StudyPizzas.this.finish();
                }
            });
            correctDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //set all ingredients back to false
                            for (int i = 0; i < ingredientList.length; i++) {
                                ingredientList[i] = false;
                            }
                            //unselect all buttons
                            for (int i = 0; i < buttons.size(); i++) {
                                buttons.get(i).setSelected(false);
                            }
                            buttons.clear();
                            Toast toast = Toast.makeText(StudyPizzas.this, "Good Luck!", Toast.LENGTH_SHORT);
                            toast.show();
                            setPizzaToStudy();
                            ScrollView mainScrollView = (ScrollView) findViewById(R.id.main_scroll_view);
                            mainScrollView.fullScroll(ScrollView.FOCUS_UP);
                            dialog.cancel();
                        }
                    });
            correctDialog.show();


        } else {
            //show how many ingredients were wrong and then restart activity
            //show correct message and go to next pizza
            AlertDialog.Builder incorrectDialog = new AlertDialog.Builder(StudyPizzas.this);
            incorrectDialog.setTitle("Sorry That Is Wrong");
            incorrectDialog.setMessage("There were " + w + " wrong or missing ingredients. Would you like to continue studying?");

            incorrectDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    StudyPizzas.this.finish();
                }
            });
            incorrectDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //set all ingredients back to false
                            for (int i = 0; i < ingredientList.length; i++) {
                                ingredientList[i] = false;
                            }
                            //unselect all buttons
                            for (int i = 0; i < buttons.size(); i++) {
                                buttons.get(i).setSelected(false);
                            }
                            buttons.clear();
                            Toast toast = Toast.makeText(StudyPizzas.this, "Good Luck!", Toast.LENGTH_SHORT);
                            toast.show();
                            setPizzaToStudy();
                            ScrollView mainScrollView = (ScrollView) findViewById(R.id.main_scroll_view);
                            mainScrollView.fullScroll(ScrollView.FOCUS_UP);
                            dialog.cancel();
                        }
                    });
            incorrectDialog.show();*/




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_study_pizzas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
