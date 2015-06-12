package texteditor;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.usman.phaedra.feely_beta.R;
import com.usman.phaedra.myapplicationfb.DispatchActivity;

import java.util.List;

public class TextEditor extends Activity implements ImageGetter{

	ImageGetter myImgGetter;
	ImageView imageSmiling, imageLaughing, imageSad, imageAngry, imageTeasing, imageInLove;
	ImageView pannelVisible;
	TextView message;
	EditText enterMessage;
	Button buttonSubmit;
	LinearLayout emojiPannel;
	String myHtml="";
	int numberOfImages = 0;
	int position =0;
    String myHtml_sending="";
    boolean pannelVisibilityFlag=false;
	//Emojis//

	private String userID="authorId";
	private String postTitle="title";
	private String postTags="tags";
	private String postContent="content";

	private Spanned spanned;
	private String smiling="<img src ='a.png'>";
	private String angry="<img src ='b.png'>";
	private String inlove="<img src ='c.png'>";
	private String laughing="<img src ='d.png'>";
	private String sad="<img src ='e.png'>";
	private String teasing="<img src ='f.png'>";
    public  ParseObject author;
    private String toBeAppended="";
    private  boolean bufferOn= false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		initViews();
		setViewsEvents();
		String code = enterMessage.getText().toString();
		myImgGetter = this;
		spanned = Html.fromHtml(code, this, null);
		message.setText(spanned);
		message.setTextSize(16);

        enterMessage.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Spanned spanned = enterMessage.getText();
                myHtml_sending = Html.toHtml(spanned);
            }

        });

	}
	private void setViewsEvents() {
		emojiPannel.setVisibility(View.INVISIBLE);

		pannelVisible.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //hide keyboard first
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(enterMessage.getWindowToken(), 0);


                if (pannelVisibilityFlag == false) {
                    emojiPannel.setVisibility(View.VISIBLE);
                    pannelVisibilityFlag = true;
                } else if (pannelVisibilityFlag == true) {
                    emojiPannel.setVisibility(View.INVISIBLE);
                    pannelVisibilityFlag = false;
                }
            }


        });

		imageSmiling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				emojiClickHandler(smiling);
			}


		});
		imageLaughing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emojiClickHandler(laughing);
			}
		});
		imageSad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emojiClickHandler(sad);
			}
		});
		imageAngry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emojiClickHandler(angry);
			}
		});
		imageTeasing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emojiClickHandler(teasing);
			}
		});
		imageInLove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emojiClickHandler(inlove);
			}
		});
        buttonSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //post the HTML Text to server
//                ParseUser currentUser = ParseUser.getCurrentUser();

               
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                Bundle extras = getIntent().getExtras();
                String email="usman@home.com";

                if (extras != null)
                    email = extras.getString("email");
                query.whereEqualTo("emailId", email);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> userList, ParseException e) {
                        if (e == null) {
                           author=userList.get(0);
                        } else {

                        }
                    }
                });
                    // do stuff with the user
                    ParseObject feeling = new ParseObject("Post");



                feeling.put("content",myHtml_sending);
                feeling.put("title","This is Post Title");
				feeling.put("email","usman@home.com");

//                    ParseACL postACL = new ParseACL(ParseUser.getCurrentUser());
//                    postACL.setPublicReadAccess(true);
//                    feeling.setACL(postACL);
                feeling.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"NOT saved",Toast.LENGTH_SHORT).show();
                        }

                    }
                }); //save locally
//                feeling.saveEventually();  //save on server



            }
        });
	}
	/**Assigns XML views to Java View variables.
	 */
	private void initViews() {
		enterMessage =(EditText) findViewById(R.id.edittext_enter_post);
		imageSmiling = (ImageView) findViewById(R.id.imageSmiling);
		imageLaughing = (ImageView) findViewById(R.id.imageLaughing);
		imageSad = (ImageView) findViewById(R.id.imageSad);
		imageAngry = (ImageView) findViewById(R.id.imageAngry);
		imageTeasing = (ImageView) findViewById(R.id.imageTeasing);
		imageInLove = (ImageView) findViewById(R.id.imageInLove);
		message = (TextView) findViewById (R.id.textview_message);     
		emojiPannel=(LinearLayout) findViewById(R.id.layout_emoji_pannel);
		pannelVisible = (ImageView) findViewById(R.id.image_pannel_visibility);
		buttonSubmit= (Button) findViewById(R.id.button_submitPost);
	}

	@Override
	public void onBackPressed() {
		
		
		//Hide emotions Panel if its there	
		if (pannelVisibilityFlag==true){
			emojiPannel.setVisibility(View.INVISIBLE);
			pannelVisibilityFlag=false;
			Toast.makeText(getApplicationContext(), "flag set false after making invisible", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public Drawable getDrawable(String arg0) {

        bufferOn=true;
		int id = 0;

		if(arg0.equals("a.png")){
			id = R.drawable.a;
		}

		if(arg0.equals("b.png")){
			id = R.drawable.b;
		}
		if(arg0.equals("c.png")){
			id = R.drawable.c;
		}
		if(arg0.equals("d.png")){
			id = R.drawable.d;
		}
		if(arg0.equals("e.png")){
			id = R.drawable.e;
		}
		if(arg0.equals("f.png")){
			id = R.drawable.f;
		}
		LevelListDrawable d = new LevelListDrawable();
		Drawable empty = getResources().getDrawable(id);
		d.addLevel(0, 0, empty);
		d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

		return d;
	}

	/** getMappedIndex(String string, int index) 
	 **/
	private int getMappedIndex(String str, int index){
		boolean flag = false;
		int parseCount=0;
		int i=0;
		String s2 = "" ;
		String s3 = "<img src=\"a.png\">";
		String s4 = "<img src=\"b.png\">";
		String s5 = "<img src=\"c.png\">";
		String s6 = "<img src=\"d.png\">";
		String s7 = "<img src=\"e.png\">";
		String s8 = "<img src=\"f.png\">";

		//for this i there was indexOutOfBound issue.. i=size=182
		for( i=0;parseCount<index;i++){

			if(str.charAt(i)=='<'){

				if(str.length()>=i+17)
					s2= str.substring(i,  i+17);
				if(str.length() >=i+17 && (s2.equals(s3) || s2.equals(s4)|| s2.equals(s5)|| s2.equals(s6)|| s2.equals(s7)|| s2.equals(s8)))
				{

					flag = false;
					parseCount++;
					i=i+16;
				}
				else
				{
					flag = true;

				}
			}
			else if(str.charAt(i)=='>'){
				flag = false;

			}
			else if(flag==false){
				parseCount++;

			}
		}
		return i;

	}
	/**emojiClickHandler(String emoji) takes emoji string with "<img src ='smiling.png'>" and 
	 * inserts in current string
	 **/
	private void emojiClickHandler(String emoji) {

		Spanned spanned= enterMessage.getText();
		String htmlString = Html.toHtml(spanned);
		int cursorPosition = enterMessage.getSelectionStart();
		int newIndex= getMappedIndex(htmlString,cursorPosition );

		htmlString= htmlString.substring(0,  newIndex) + emoji + htmlString.substring(newIndex, htmlString.length());
        myHtml_sending=htmlString+emoji;
        bufferOn=true;
		spanned = Html.fromHtml(htmlString, myImgGetter, null);
		enterMessage.setText(spanned);
		enterMessage.setSelection(cursorPosition+1);

	}
}
