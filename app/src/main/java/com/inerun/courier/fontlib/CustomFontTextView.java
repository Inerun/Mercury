package com.inerun.courier.fontlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.inerun.courier.R;


public class CustomFontTextView extends TextView {

	public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);

	}

	public CustomFontTextView(Context context) {
		super(context);
		init(null);
	}

	private void init(AttributeSet attrs) {
		if (!isInEditMode()) {
			if (attrs != null) {
				TypedArray a = getContext().obtainStyledAttributes(attrs,
						R.styleable.CustomFontTextView);
				String fontName = a
						.getString(R.styleable.CustomFontTextView_textviewfontName);
				if (fontName != null) {
					Typeface myTypeface = Typeface.createFromAsset(getContext()
							.getAssets(), "fonts/" + fontName);
					setTypeface(myTypeface);
				}
				a.recycle();
			}
		}
		else
		{
//			if (attrs != null) {
//				setText("F" + attrs.getAttributeValue(Att));
//			}else
//			{
//				setText("F" + attrs);
//			}
//				setText("F" );

		}
	}
}
