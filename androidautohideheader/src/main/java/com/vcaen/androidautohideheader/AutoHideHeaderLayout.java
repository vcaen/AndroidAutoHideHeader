/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Vadim Caen <github.com/vcaen>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.vcaen.androidautohideheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


public class AutoHideHeaderLayout extends RelativeLayout {


    private static final long MIN_EVENT_TIME = 200;
    private static final long ANIMATION_DURATION = 200;

    enum HeaderState {
        HIDDEN,
        OPENING,
        OPENED
    }


    View _headerLayout;
    BodyLayout _bodyLayout;
    HeaderState _state;

    public AutoHideHeaderLayout(Context context) {
        super(context);
        init(context);
    }


    public AutoHideHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoHideHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        _state = HeaderState.OPENED;
        _bodyLayout = new BodyLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        _bodyLayout.setLayoutParams(params);
        addView(_bodyLayout);
    }


    /**
     * Set the header view. The view should have a fixed height.
     *
     * @param header The {@link View} representing the header
     */
    public void setHeader(View header) {
        if (_headerLayout != null) {
            removeView(header);
        }
        _headerLayout = header;
        if (header != null) {
            header.setId(R.id.header);
            _headerLayout.setY(getY());
            addView(header, 0);
            _bodyLayout.setY(_headerLayout.getY() + _headerLayout.getHeight());
            requestLayout();
        }
    }

    /**
     * Set the header view. The view should have a fixed height.
     *
     * @param id The id of the layout representing the Header
     */
    public void setHeader(int id) {
        View header = LayoutInflater.from(getContext()).inflate(id, this, false);
        setHeader(header);
    }

    /**
     * Set the Body which will be under the Header
     *
     * @param bodyView The {@link View} representing the body
     */
    public void setBodyView(View bodyView) {
        _bodyLayout.setInnerView(bodyView);
        requestLayout();
    }

    @Override public void addView(View child) {
        if (getChildCount() <= 2) {
            super.addView(child);
        }
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if(!(child instanceof BodyLayout)) {
                if (_headerLayout == null) {
                    removeView(child);
                    setHeader(child);
                } else if (!_bodyLayout.hasInnerView()) {
                    removeView(child);
                    setBodyView(child);
                } else if (i > 1){
                    removeViewAt(i);
                }
            }
        }

        if (isInEditMode()) return;
        _bodyLayout.setY(_headerLayout.getY() + _headerLayout.getHeight());
    }

    /**
     * The body handling the touch events
     */
    class BodyLayout extends FrameLayout {


        float movedDistance;
        private View _innerView;
        private float _origY;
        private float _origHeaderY;
        private float _origBodyY;

        public BodyLayout(Context context) {
            super(context);

        }


        @Override public boolean dispatchTouchEvent(MotionEvent event) {

            movedDistance = event.getRawY() - _origY;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    _origY = event.getRawY();
                    _origBodyY = getY();
                    _origHeaderY = _headerLayout.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("MovedDistance", "State : " + _state.name() + " Distance : " + movedDistance);

                    if (_state == HeaderState.OPENING || _state == HeaderState.HIDDEN && movedDistance > 1 || _state == HeaderState.OPENED && movedDistance < -1) {


                        if (_state != HeaderState.OPENED && _headerLayout.getY() + _headerLayout.getHeight() >= AutoHideHeaderLayout.this.getY() + _headerLayout.getHeight()) {

                            // The header is OPENED, we reset the event as it was a down event and pass it to the child view
                            _state = HeaderState.OPENED;
                            Log.d("State", _state.name());
                            event.setAction(MotionEvent.ACTION_DOWN);
                            _origY = event.getRawY();
                            _origBodyY = getY();
                            _origHeaderY = _headerLayout.getY();
                            return super.dispatchTouchEvent(event);

                        } else if (_state != HeaderState.HIDDEN && _headerLayout.getY() + _headerLayout.getHeight() <= AutoHideHeaderLayout.this.getY()) {

                            // The header is HIDDEN, we reset the event as it was a down event and pass it to the child view
                            _state = HeaderState.HIDDEN;
                            Log.d("State", _state.name());
                            event.setAction(MotionEvent.ACTION_DOWN);
                            _origY = event.getRawY();
                            _origBodyY = getY();
                            _origHeaderY = _headerLayout.getY();
                            return super.dispatchTouchEvent(event);

                        } else {

                            // We follow the touch gestur
                            _state = HeaderState.OPENING;
                            Log.d("State", _state.name());


                            if (_origHeaderY + movedDistance > AutoHideHeaderLayout.this.getY()) {
                                // if the movement if greater than the place where the views should be,
                                // (the top of the parent view) we block it to the desired position

                                Log.d("State", "1");
                                _headerLayout.setY(AutoHideHeaderLayout.this.getY());
                                setY(AutoHideHeaderLayout.this.getY() + _headerLayout.getHeight());

                            } else if (_origHeaderY + movedDistance < AutoHideHeaderLayout.this.getY() - _headerLayout.getHeight()) {
                                // if the movement if greater than the place where the views should be,
                                // (the top of the parent view) we block it to the desired position,
                                // with the header hidden on top of the body
                                Log.d("State", "2");
                                _headerLayout.setY(AutoHideHeaderLayout.this.getY() - _headerLayout.getHeight());
                                setY(AutoHideHeaderLayout.this.getY());

                            } else {

                                // Just move the body and header as much as the touch gesture
                                Log.d("State", "3");
                                setY(_origBodyY + movedDistance);
                                _headerLayout.setY(_origHeaderY + movedDistance);
                            }
                        }
                    }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Handle the fling gesture
                    long time = event.getEventTime() - event.getDownTime();
                    if (time < MIN_EVENT_TIME) {
                        if (Math.abs(movedDistance) < _headerLayout.getHeight()) {
                            if (movedDistance > 0) {
                                _headerLayout.animate().y(AutoHideHeaderLayout.this.getY()).setDuration(ANIMATION_DURATION).start();
                                _bodyLayout.animate().y(AutoHideHeaderLayout.this.getY() + _headerLayout.getHeight()).setDuration(ANIMATION_DURATION).start();
                            } else if (movedDistance < 0) {
                                _headerLayout.animate().y(AutoHideHeaderLayout.this.getY() - _headerLayout.getHeight()).setDuration(ANIMATION_DURATION).start();
                                _bodyLayout.animate().y(AutoHideHeaderLayout.this.getY()).setDuration(ANIMATION_DURATION).start();
                            }
                        }

                    }

            }
            return super.dispatchTouchEvent(event);
        }


        public void setInnerView(View innerView) {
            if (_innerView != null) {
                removeView(_innerView);
            }
            _innerView = innerView;
            if (innerView != null) {
                addView(innerView);

            }
        }

        public boolean hasInnerView() {
            return _innerView != null;
        }
    }

}
