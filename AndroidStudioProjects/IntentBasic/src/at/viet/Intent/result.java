/**
 * Licensed to Open-Ones Group under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Open-Ones Group licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package at.viet.Intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * @author nhutlt
 *
 */
public class result extends Activity {
    private int valueA, valueB, iSum, iMul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        
        final Button nextButton = (Button) findViewById(R.id.btnNex);
        final Button resetButton = (Button) findViewById(R.id.btnReset);
        final EditText sum = (EditText) findViewById(R.id.txtSum);
        final EditText mul = (EditText) findViewById(R.id.txtMul);
        Bundle receivedData = this.getIntent().getExtras();
        
        valueA = receivedData.getInt("valueA");
        valueB = receivedData.getInt("valueB");
        iSum = valueA + valueB;
        iMul = valueA * valueB;       
        sum.setText(String.valueOf(iSum));
        mul.setText(String.valueOf(iMul));   
        
        OnClickListener nextButtonAction = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub  
                valueA = iSum;
                valueB = iMul;
                iSum = valueA + valueB;
                iMul = valueA * valueB;
                sum.setText(sum.getText().toString() + ", " + String.valueOf(iSum));
                mul.setText(mul.getText().toString() + ", " + String.valueOf(iMul));   
            }
        };
        
        OnClickListener resetButtonAction = new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(result.this, input.class);
                startActivity(i);
                finish();
            }
        };
        nextButton.setOnClickListener(nextButtonAction);
        resetButton.setOnClickListener(resetButtonAction);
        
    }
}
