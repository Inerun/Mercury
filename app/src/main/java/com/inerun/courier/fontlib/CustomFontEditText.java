package com.inerun.courier.fontlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.inerun.courier.R;
import com.rengwuxian.materialedittext.MaterialEditText;


public class CustomFontEditText extends MaterialEditText {

	public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public CustomFontEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);

	}

	public CustomFontEditText(Context context) {
		super(context);
		init(null);
	}

	private void init(AttributeSet attrs) {
		if (!isInEditMode()) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.CustomFontEditTextView);
			String fontName = a
					.getString(R.styleable.CustomFontEditTextView_edittextfontName);
			if (fontName != null) {
				Typeface myTypeface = Typeface.createFromAsset(getContext()
						.getAssets(), "fonts/" + fontName);
				setTypeface(myTypeface);
			}
			a.recycle();
		}
		}
	}
	

}
