package fr.vadimcaen.autohideheader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


public class AutoHideHeaderListView extends RelativeLayout {


    enum HeaderState {
        HIDDEN,
        OPENING,
        OPENED
    }


    View _headerLayout;
    BodyLayout _bodyLayout;
    HeaderState _state;

    public AutoHideHeaderListView(Context context) {
        super(context);
        init(context);
    }


    public AutoHideHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoHideHeaderListView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void setHeader(View header) {
        if (_headerLayout != null) {
            removeViewAt(0);
        }
        _headerLayout = header;
        if (header != null) {
            header.setId(R.id.header);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            _headerLayout.setY(getY());
            _headerLayout.setLayoutParams(params);
            addView(header, 0);
        }
        _bodyLayout.setY(_headerLayout.getY() + _headerLayout.getHeight());
    }

    public void setHeader(int id) {
        if (_headerLayout != null) {
            removeView(_headerLayout);
        }

        View header = LayoutInflater.from(getContext()).inflate(id, this, false);
        _headerLayout = header;
        if (header != null) {
            header.setId(R.id.header);
        }
        addView(header, 0);
        _bodyLayout.setY(_headerLayout.getY() + _headerLayout.getHeight());
        requestLayout();
    }

    public void setBodyView(View bodyView) {
        _bodyLayout.setInnerView(bodyView);
        requestLayout();
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        _bodyLayout.setY(_headerLayout.getY() + _headerLayout.getHeight());
    }

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

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    _origY = event.getRawY();
                    _origBodyY = getY();
                    _origHeaderY = _headerLayout.getY();
                    break;
                case MotionEvent.ACTION_MOVE:

                    movedDistance = event.getRawY() - _origY;
                    Log.d("MovedDistance", "State : " + _state.name() + " Distance : " + movedDistance);




                    if (_state == HeaderState.OPENING || _state == HeaderState.HIDDEN && movedDistance > 1 || _state == HeaderState.OPENED && movedDistance < -1) {

                        if (_state != HeaderState.OPENED && _headerLayout.getY() + _headerLayout.getHeight() >= AutoHideHeaderListView.this.getY() + _headerLayout.getHeight()) {
                            _state = HeaderState.OPENED;
                            Log.d("State", _state.name());
                            event.setAction(MotionEvent.ACTION_DOWN);
                            _origY = event.getRawY();
                            _origBodyY = getY();
                            _origHeaderY = _headerLayout.getY();
                            return super.dispatchTouchEvent(event);

                        } else if (_state != HeaderState.HIDDEN && _headerLayout.getY() + _headerLayout.getHeight() <= AutoHideHeaderListView.this.getY()) {
                            _state = HeaderState.HIDDEN;
                            Log.d("State", _state.name());
                            event.setAction(MotionEvent.ACTION_DOWN);
                            _origY = event.getRawY();
                            _origBodyY = getY();
                            _origHeaderY = _headerLayout.getY();
                            return super.dispatchTouchEvent(event);

                        } else {
                            _state = HeaderState.OPENING;
                            Log.d("State", _state.name());

                            if (_origHeaderY + movedDistance > AutoHideHeaderListView.this.getY()) {
                            //if (_origHeaderY + movedDistance > _origHeaderY + _headerLayout.getHeight()) {

                                Log.d("State", "1");
                                _headerLayout.setY(AutoHideHeaderListView.this.getY());
                                setY(AutoHideHeaderListView.this.getY() + _headerLayout.getHeight());

                            } else if (_origHeaderY + movedDistance < AutoHideHeaderListView.this.getY() - _headerLayout.getHeight()) {
                                Log.d("State", "2");
                                _headerLayout.setY(AutoHideHeaderListView.this.getY() - _headerLayout.getHeight());
                                setY(AutoHideHeaderListView.this.getY());

                            } else {
                                Log.d("State", "3");
                                setY(_origBodyY + movedDistance);
                                _headerLayout.setY(_origHeaderY + movedDistance);
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

    }

}
