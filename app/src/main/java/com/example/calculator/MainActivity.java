package com.example.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    //Declare the widgets to be used
    Button btnAC, btnDel, btnRightBracketBracket, btnLeftBracket, btnSeven, btnEight, btnNine, btnPlus, btnFour, btnFive, btnSix, btnMinus, btnOne, btnTwo, btnThree, btnMultiply, btnDecimal, btnZero, btnEquals, btnDivide, btnExponent, btnTan, btnCos, btnSin;
    TextView txtExpression, txtPrecision, txtResult, txtDuration;

    String precision, expression;
    //To be passed as parameters for calculations
    Float partOne;
    Float partTwo;

    //The return after a calculation is made
    Float result;
    Long z0;
    Long z1;
    Long z2;
    float a;
    float b;
    float c;
    float d;
    Long m;
    int n;
    String operation;
    String duration;

    Long res;
    private static int TIME_OUT = 4000;
    Timer timer;


    static long N = 1000000007L; // prime modulo value


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///Instantiating the widgets
        btnAC = (Button) findViewById(R.id.btnAC);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnRightBracketBracket = (Button) findViewById(R.id.btnRightBracket);
        btnLeftBracket = (Button) findViewById(R.id.btnleftBracket);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMultiply = (Button) findViewById(R.id.btnMultipy);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnDecimal = (Button) findViewById(R.id.btnDecimal);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnEquals = (Button) findViewById(R.id.btnEquals);
        btnExponent = (Button) findViewById(R.id.btnExponent);
        btnTan = (Button) findViewById(R.id.btnTan);
        btnSin = (Button) findViewById(R.id.btnSin);
        btnCos = (Button) findViewById(R.id.btnCos);


        txtExpression = (TextView) findViewById(R.id.txtexpression);
        txtPrecision = (TextView) findViewById(R.id.txtPrecision);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtDuration = (TextView) findViewById(R.id.txtDuration);


        //Adding event listeners to the buttons
        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partOne = null;
                partTwo = null;
                result = null;
                txtExpression.setText("");
                txtPrecision.setText("");
                txtResult.setText("");

            }
        });
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation = "*";
                if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                    txtExpression.setText("");
                } else if (partOne == null) {
                    partOne = Float.parseFloat((String) txtExpression.getText());
                    txtExpression.setText("");

                }

            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation = "-";

                if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                    txtExpression.setText("");
                } else if (partOne == null) {
                    partOne = Float.parseFloat((String) txtExpression.getText());
                    txtExpression.setText("");
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation = "+";

                if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                    txtExpression.setText("");
                } else if (partOne == null) {
                    partOne = Float.parseFloat((String) txtExpression.getText());
                    txtExpression.setText("");
                }

            }
        });
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation = "/";
                if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                    txtExpression.setText("");
                } else if (partOne == null) {
                    partOne = Float.parseFloat((String) txtExpression.getText());
                    txtExpression.setText("");

                }
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (partOne != null) {
                    partTwo = Float.valueOf((String) txtExpression.getText());

                    if (operation.equalsIgnoreCase("/")) {


                        result = Float.parseFloat(String.valueOf(divisionUsingLoop(partOne, partTwo)));


                        partOne = (result);
                        if (!txtPrecision.getText().toString().equalsIgnoreCase("") && !txtPrecision.getText().toString().contains("Precision")) {
                            Integer prec = Integer.parseInt((String) txtPrecision.getText());
                            double roundOff = Math.round(result * Math.pow(10, prec)) / Math.pow(10, prec);
                            txtResult.setText(roundOff + "");

                            txtExpression.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a precision", Toast.LENGTH_LONG).show();
                        }


                    } else if (operation.equalsIgnoreCase("*")) {

                        result = Float.parseFloat(String.valueOf(multiply(partOne, partTwo)));

                        partOne = (result);
                        if (!txtPrecision.getText().toString().equalsIgnoreCase("") && !txtPrecision.getText().toString().contains("Precision")) {
                            Integer prec = Integer.parseInt((String) txtPrecision.getText());
                            double roundOff = Math.round(result * Math.pow(10, prec)) / Math.pow(10, prec);
                            txtResult.setText(roundOff + "");

                            txtExpression.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a precision", Toast.LENGTH_LONG).show();
                        }

                    } else if (operation.equalsIgnoreCase("+")) {
                        result = Float.valueOf(partOne + partTwo);
                        partOne = (result);
                        if (!txtPrecision.getText().toString().equalsIgnoreCase("") && !txtPrecision.getText().toString().contains("Precision")) {
                            Integer prec = Integer.parseInt((String) txtPrecision.getText());
                            double roundOff = Math.round(result * Math.pow(10, prec)) / Math.pow(10, prec);
                            txtResult.setText(roundOff + "");

                            txtExpression.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a precision", Toast.LENGTH_LONG).show();
                        }

                    } else if (operation.equalsIgnoreCase("-")) {
                        result = Float.valueOf(partOne - partTwo);

                        partOne = (result);
                        if (!txtPrecision.getText().toString().equalsIgnoreCase("") && !txtPrecision.getText().toString().contains("Precision")) {
                            Integer prec = Integer.parseInt((String) txtPrecision.getText());
                            double roundOff = Math.round(result * Math.pow(10, prec)) / Math.pow(10, prec);
                            txtResult.setText(roundOff + "");

                            txtExpression.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a precision", Toast.LENGTH_LONG).show();
                        }


                    } else if (operation.equalsIgnoreCase("^")) {

                        res = exponentiation(partOne, partTwo);


                        txtResult.setText(res + "");
                        txtExpression.setText("");

                    }
                }
            }

        });

        //To edit the duration we set a listener for the txtdURATION button so that when it is clicked the buttons pressed edit that text field
        txtDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDuration.setText("");
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!txtDuration.getText().equals(""))
                            duration = (String) txtDuration.getText();
                        Integer count = countChar(duration);
                        count -= 1;
                        StringBuffer sb = new StringBuffer(duration);
                        sb = sb.deleteCharAt(count);
                        txtDuration.setText(sb.toString());
                    }
                });

                btnZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnZero.getText();
                        txtDuration.setText(duration);
                    }
                });


                btnOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnOne.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnTwo.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnThree.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnFour.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnFive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnFive.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnSix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnSix.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnSeven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnSeven.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnEight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnEight.getText();
                        txtDuration.setText(duration);
                    }
                });
                btnNine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtDuration.getText().toString().equalsIgnoreCase("Duration")) {
                            txtDuration.setText("");
                        }
                        duration = (String) txtDuration.getText();
                        duration = duration + (String) btnNine.getText();
                        txtDuration.setText(duration);
                    }
                });
            }
        });
//To edit the duration we set a listener for the txtExpression button so that when it is clicked the buttons pressed edit that text field
        txtExpression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtExpression.setText("");
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!txtExpression.getText().equals(""))
                            expression = (String) txtExpression.getText();
                        Integer count = countChar(expression);
                        count -= 1;
                        StringBuffer sb = new StringBuffer(expression);
                        sb = sb.deleteCharAt(count);
                        txtPrecision.setText(sb.toString());
                    }
                });

                btnDecimal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnDecimal.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnZero.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnOne.getText();
                        txtExpression.setText(expression);


                    }
                });
                btnTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnTwo.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnThree.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnFour.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnFive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnFive.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnSix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnSix.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnSeven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnSeven.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnEight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnEight.getText();
                        txtExpression.setText(expression);

                    }
                });
                btnNine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtExpression.getText().toString().equalsIgnoreCase("Value")) {
                            txtExpression.setText("");
                        }
                        expression = (String) txtExpression.getText();
                        expression = expression + (String) btnNine.getText();
                        txtExpression.setText(expression);

                    }
                });
            }
        });

//To edit the precision we set a listener for the txtPrecision button so that when it is clicked the buttons pressed edit that text field
        txtPrecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPrecision.setText("");
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!txtPrecision.getText().equals(""))
                            precision = (String) txtPrecision.getText();
                        Integer count = countChar(precision);
                        count -= 1;
                        StringBuffer sb = new StringBuffer(precision);
                        sb = sb.deleteCharAt(count);
                        txtPrecision.setText(sb.toString());
                    }
                });
                btnOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnOne.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnTwo.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnThree.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnFour.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnFive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnFive.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnSix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnSix.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnSeven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnSeven.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnEight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnEight.getText();
                        txtPrecision.setText(precision);
                    }
                });
                btnNine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtPrecision.getText().toString().equalsIgnoreCase("Precision")) {
                            txtPrecision.setText("");
                        }
                        precision = (String) txtPrecision.getText();
                        precision = precision + (String) btnNine.getText();
                        txtPrecision.setText(precision);
                    }
                });
            }
        });

        //Buttons to perform trigonometric functions
        btnTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtExpression.getText().toString() != null) {
                    if (!(txtExpression.getText().toString().equalsIgnoreCase("Value"))) {

                        Double res = Math.tan(Double.parseDouble(txtExpression.getText().toString()));
                        txtResult.setText(String.valueOf(res));
                    }
                }
            }
        });
        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtExpression.getText().toString() != null) {
                    if (!(txtExpression.getText().toString().equalsIgnoreCase("Value"))) {

                        Double res = Math.sin(Double.parseDouble(txtExpression.getText().toString()));
                        txtResult.setText(String.valueOf(res));
                    }
                }
            }
        });
        btnCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtExpression.getText().toString() != null) {
                    if (!(txtExpression.getText().toString().equalsIgnoreCase("Value"))) {

                        Double res = Math.cos(Double.parseDouble(txtExpression.getText().toString()));
                        txtResult.setText(String.valueOf(res));
                    }
                }
            }
        });

        //Listener for button to get exponent
        btnExponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partOne = Float.parseFloat((String) txtExpression.getText());
                txtExpression.setText("");
                operation = "^";

            }
        });

    }


    /**
     * Function to calculate length or number of digits in a number
     **/
    public Integer countChar(String str) {
        int i = 0;
        Character ch = null;
        while (true) {
            try {
                ch = str.charAt(i);
            } catch (Exception e) {
                e.printStackTrace();
                ch = null;
            }

            if (ch != null) {
                i += 1;
            } else {
                break;
            }
        }

        return i;
    }

    /**
     * Function to multiply two numbers
     **/
    public Float multiply(Float x, Float y) {
        partOne = x;
        partTwo = y;

        duration = txtDuration.getText().toString();
        if (duration.contains("Enter") || duration.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Enter the time", Toast.LENGTH_LONG).show();
        } else {

            Long currentTime = System.currentTimeMillis();
            Long endTime = System.currentTimeMillis() + (Integer.parseInt(duration) * 1000);


            int size1 = countChar(String.valueOf(partOne));
            int size2 = countChar(String.valueOf(partTwo));
            /** Maximum of lengths of number **/
            n = Math.max(size1, size2);

            /** max length divided, rounded up **/
            n = (int) ((n / 2) + (N % 2));
            /** for small values directly multiply **/
//        if (N < 10)
//            return x * y;
            /** multiplier **/
            m = (long) Math.pow(10, N);
            /** compute sub expressions **/


            /** compute sub expressions **/
            b = partOne / m;
            a = partOne - (b * m);
            d = partTwo / m;
            c = partTwo - (d * N);

            if (endTime <= System.currentTimeMillis()) {
                //Alert dialog to be shown if calculation takes longer than duration stipulated
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Continue Running?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setTxtResult(result + "1");

                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


                return result;

            }

            z0 = Long.parseLong(String.valueOf(multiply(a, c)));
            z1 = Long.parseLong(String.valueOf(multiply(a + b, c + d)));
            z2 = Long.parseLong(String.valueOf(multiply(b, d)));
//Exit point for recursive function
            result = Float.parseFloat(String.valueOf(z0 + ((z1 - z0 - z2) * m) + (z2 * (long) (Math.pow(10, 2 * n)))));

        }
        return result;

    }


    public Float divisionUsingLoop(Float a, Float b) {
        duration = txtDuration.getText().toString();
        if (duration.contains("Enter") || duration.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Enter the time", Toast.LENGTH_LONG).show();
        } else {

            Long endTime = System.currentTimeMillis() + (Integer.parseInt(duration) * 1000);
            int absA = (int) Math.abs(a);
            int absB = (int) Math.abs(b);

            long temp = absA;
            result = Float.valueOf(0);
            while (temp >= 0) {
                temp -= absB;

                if (endTime <= System.currentTimeMillis()) {
                    //Alert dialog to be shown if calculation takes longer than duration stipulated
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Continue Running?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    setTxtResult(result + "");

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                }
                if (temp >= 0)
                    result++;
            }
        }
        return result;
    }

//Functon to get the exponent
    public long exponentiation(Float base, Float exp) {
        if (exp == 0)
            return 1;

        if (exp == 1)
            return (long) (base % N);

        long t = exponentiation(base, exp / 2);
        t = (t * t) % N;

        // if exponent is even value
        if (exp % 2 == 0)
            return t;

            // if exponent is odd value
        else
            return (long) (((base % N) * t) % N);
    }

//Function to set result if the calculation takes too long and the user opts to end it there
    public void setTxtResult(String str) {
        txtResult.setText(str);
    }


}
