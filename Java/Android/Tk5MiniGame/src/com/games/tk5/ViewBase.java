package com.games.tk5;

import com.games.tk5.util.AudioPlayer;
import com.games.tk5.util.Logs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class ViewBase extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder m_Holder;
	private Refresh m_Refresh = null;	
	private GameStatus m_GameStatus = GameStatus.Runing;
	private ViewStatus m_ViewStatus = ViewStatus.Entry;
	private int m_Frame = 0;
	private ViewCallBack m_ViewCallBack;	
	private DisplayMetrics m_DisplayMetrics;
	private final Rect m_Sreen_Rect = new Rect();
	private int m_Screen_Width = 0;
	private int m_Screen_Height = 0;
				
	protected ViewBase(Context context, ViewCallBack callback) {
		super(context);
		m_ViewCallBack = callback;
		m_Holder = getHolder();
		m_Holder.addCallback(this);
		m_DisplayMetrics = getResources().getDisplayMetrics();
		m_Screen_Width = m_DisplayMetrics.widthPixels;
		m_Screen_Height = m_DisplayMetrics.heightPixels;
		m_Sreen_Rect.set(0, 0, m_Screen_Width, m_Screen_Height);
	}
	
	protected int getScreenWidth() {
		return m_Screen_Width;		
	}
	
	protected int getScreenHeight() {
		return m_Screen_Height;		
	}
	
	protected Rect getSreenRect() {
		return m_Sreen_Rect;
	}	
	
	protected DisplayMetrics getDisplayMetrics() {
		return m_DisplayMetrics;
	}
	
	protected ViewCallBack getViewCallBack() {
		return m_ViewCallBack;
	}
			
	protected Path setTranslation(PointF left, PointF right, float step, boolean up) {
		float x1 = left.x;
		float y1 = left.y;
		float x2 = right.x;
		float y2 = right.y;
		
		if (up) {
			if (x1 == 0f) {
				y1 -= (float)getScreenHeight() * step;
				x2 += (float)getScreenWidth() * step;
				
				if (y1 <= 0f || x2 >= getScreenWidth()) {
					x1 = (float)getScreenWidth() * step;
					y1 = 0f;
					x2 = (float)getScreenWidth() - 1f; 
					y2 = (float)getScreenHeight() - (float)getScreenHeight() * step;
				}
			} else {
				if (y1 == 0f) {
					x1 += (float)getScreenWidth() * step;
					y2 -= (float)getScreenHeight() * step;
					
					if (x1 >= (float)getScreenWidth() || y2 <= 0f)
						return null;					
				}
			}
		} else {
			if (y1 == 0f) {
				x1 -= (float)getScreenWidth() * step;
				y2 += (float)getScreenHeight() * step;
				
				if (x1 <= 0f || y2 >= (float)getScreenHeight()) {
					x1 = 0f;
					y1 = (float)getScreenHeight() * step;
					x2 = (float)getScreenWidth() - (float)getScreenWidth() * step;
					y2 = (float)getScreenHeight() - 1f; 
				}
			} else {
				if (x1 == 0f) {
					y1 += (float)getScreenHeight() * step;
					x2 -= (float)getScreenWidth() * step;
					
					if (y1 >= (float)getScreenHeight() || x2 <= 0f)
						return null;					
				}
			}
		}
		
		left.set(x1, y1);
		right.set(x2, y2);
		
		Path path = new Path();
		if (up) {
			if (x1 == 0f) {
				path.moveTo(0f, y1);
				path.lineTo(x2, y2);
				path.lineTo(0f, (float)getScreenHeight() - 1f);
			} else {
				path.moveTo(0f, 0f);
				path.lineTo(x1, y1);
				path.lineTo(x2, y2);
				path.lineTo((float)getScreenWidth() - 1f, (float)getScreenHeight() - 1f);
				path.lineTo(0f, (float)getScreenHeight() - 1f);
			}
		} else {
			if (y1 == 0f) {
				path.moveTo(0f, 0f);
				path.lineTo(x1, y1);
				path.lineTo(x2, y2);
				path.lineTo((float)getScreenWidth() - 1f, (float)getScreenHeight() - 1f);
				path.lineTo(0f, (float)getScreenHeight() - 1f);
			} else {
				path.moveTo(x1, y1);
				path.lineTo(x2, y2);
				path.lineTo(0f, (float)getScreenHeight() - 1f);
			}
		}
		
		return path;
	}
	
	protected Path setTranslation(PointF point, float step, boolean up) {
		float x1 = point.x;
		float y1 = point.y;
		float x2 = 0f;
		float y2 = ((float)getScreenHeight() - 1f) / 2f;
		
		if (up) {
			if (y1 == 0f) {
				x1 -= (float)getScreenWidth() / 2f * step;
				if (x1 < 0f) {
					x1 = 0f;
					y1 = 1f;
				}
			} else
				y1 += (float)getScreenHeight() / 2f * step;
			
			if (y1 > y2)				
				return null;			
		} else {
			x2 = (float)getScreenWidth() - 1f / 2f;
			if (y1 == (float)getScreenHeight() - 1f) {
				x1 += (float)getScreenWidth() / 2f * step;
				if (x1 > (float)getScreenWidth()) {
					x1 = (float)getScreenWidth() - 1f;
					y1 = (float)getScreenHeight() - 2f;
				}
			} else
				y1 -= (float)getScreenHeight() / 2f * step;
			
			if (y1 < y2)
				return null;
		}
		
		point.set(x1, y1);
		
		Path path = new Path();
		if (up) {
			if (y1 == 0) {
				path.moveTo(x2, y2);
				path.lineTo(((float)getScreenWidth() - 1f) / 2f, ((float)getScreenHeight() - 1f) / 2f);
				path.lineTo(x1, 0f);
				path.lineTo(0f, 0f);				
			} else {
				path.moveTo(x2, y2);
				path.lineTo(((float)getScreenWidth() - 1f) / 2f, y2);
				path.lineTo(x1, y1);
			}
		} else {
			if (y1 == (float)getScreenHeight() - 1f) {
				path.moveTo(x2, y2);
				path.lineTo(((float)getScreenWidth() - 1f) / 2f, ((float)getScreenHeight() - 1f) / 2f);
				path.lineTo(x1, (float)getScreenHeight() - 1f);
				path.lineTo((float)getScreenWidth() - 1f, (float)getScreenHeight() - 1f);
			} else {
				path.moveTo(x2, y2);
				path.lineTo(((float)getScreenWidth() - 1f) / 2f, y2);
				path.lineTo(x1, y1);
			}
		}
		
		return path;
	}	
		
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
	}
			
	public boolean setKeyDown(int keyCode, KeyEvent event) {		
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
			return onBack();
		else
			return false;
	}
		
	protected enum GameStatus {
		Pause, Runing, Destroy
	}

	protected enum ViewStatus {
		Entry, Select, Starting, Started, Ending, Leave
	}	
			
	protected GameStatus getGameStatus() {
		return m_GameStatus;
	}
	
	protected void setGameStatus(GameStatus gameStatus) {
		m_GameStatus = gameStatus;
	}
	
	protected ViewStatus getViewStatus() {
		return m_ViewStatus;
	}
	
	protected void setViewStatus(ViewStatus viewStatus) {
		m_ViewStatus = viewStatus;
	}
			
	protected void setFrame(int frame) {
		m_Frame = frame;
	}
	
	protected int getFrame() {
		return m_Frame;
	}
	
	class Refresh extends Thread {
		//开始	黑->界面(右上角->左下角)
		//结束	界面->黑(左下角->右上角)主界面
						
        @Override  
        public void run() {
        	onPrepare();
        	
        	while (true) {
        		try {
    				sleep(35);
    			} catch (InterruptedException e) {				
    				
    			}
        		
        		GameStatus gameStatus = getGameStatus();
        		if (gameStatus == GameStatus.Pause) {
        			pause();
        			continue;        			
        		}
        		
        		if (gameStatus == GameStatus.Destroy) {
        			onDestroy();
        			break;
        		}
        		        		        		
        		ViewStatus viewStatus = getViewStatus();
        		if (viewStatus == ViewStatus.Entry) {
        			int frame = getFrame();
        			entryView(frame);
        			if (frame > -1)
        				frame++;
        			        			
        			continue;        			
        		}
        		        		
        		if (viewStatus == ViewStatus.Select) {        			
        			int frame = getFrame();
        			selectView(frame);
        			if (frame > -1)
        				frame++;
        			        			
        			continue;
        		}
        		
        		if (viewStatus == ViewStatus.Starting) {        			
        			int frame = getFrame();
        			startingView(frame);
        			if (frame > -1)
        				frame++;
        			        			
        			continue;
        		}
        		
        		if (viewStatus == ViewStatus.Started)
        			drawView();
        		        		
        		if (viewStatus == ViewStatus.Ending) {        			
        			int frame = getFrame();
        			endingView(frame);
        			if (frame > -1)
        				frame++;
        			        			
        			continue;
        		}
        		
        		if (viewStatus == ViewStatus.Leave) {        			
        			int frame = getFrame();
        			if (leaveView(frame))
        				break;
        			else {
        				if (frame > -1)
            				frame++;
        				
        				continue;
        			}        			
        		}        		
        	}
        	
        	exit();
        }       
    }
	
	private void pause() {		
		onPause();
	}
			
	private void entryView(int frame) {
		Canvas canvas = null;
		
		try {
			canvas = m_Holder.lockCanvas();
			if (canvas != null) {
				onEntryView(canvas, frame);
			}
		} catch (Exception e) {
			Logs.LogsError(e);
		} finally {
			if (canvas != null)
				m_Holder.unlockCanvasAndPost(canvas);
		}
	}
	
	private void selectView(int frame) {
		Canvas canvas = null;
		
		try {
			canvas = m_Holder.lockCanvas();
			if (canvas != null) {
				onSelectView(canvas, frame);
			}
		} catch (Exception e) {
			Logs.LogsError(e);
		} finally {
			if (canvas != null)
				m_Holder.unlockCanvasAndPost(canvas);
		}
	}
	
	private void startingView(int frame) {
		Canvas canvas = null;
		
		try {
			canvas = m_Holder.lockCanvas();
			if (canvas != null) {
				onStartingView(canvas, frame);
			}
		} catch (Exception e) {
			Logs.LogsError(e);
		} finally {
			if (canvas != null)
				m_Holder.unlockCanvasAndPost(canvas);
		}
	}
	
	private void drawView() {
		Canvas canvas = null;
		
		try {
			canvas = m_Holder.lockCanvas();
			if (canvas != null) {
				onDrawView(canvas);
			}
		} catch (Exception e) {
			Logs.LogsError(e);
		} finally {
			if (canvas != null)
				m_Holder.unlockCanvasAndPost(canvas);
		} 
	}
	
	private void endingView(int frame) {
		Canvas canvas = null;
		
		try {
			canvas = m_Holder.lockCanvas();
			if (canvas != null) {
				onEndingView(canvas, frame);
			}
		} catch (Exception e) {
			Logs.LogsError(e);
		} finally {
			if (canvas != null)
				m_Holder.unlockCanvasAndPost(canvas);
		} 
	}
	
	private boolean leaveView(int frame) {
		boolean ret = true;
		Canvas canvas = null;
		
		try {
			canvas = m_Holder.lockCanvas();
			if (canvas != null) {
				ret = onLeaveView(canvas, frame);
			}
		} catch (Exception e) {
			Logs.LogsError(e);
		} finally {
			if (canvas != null)
				m_Holder.unlockCanvasAndPost(canvas);
		}
		
		return ret;
	}
	
	private void exit() {		
		onExit();
	}
	
	public boolean init() {
		if (onInit()) {
			m_Refresh = new Refresh();		
			m_Refresh.start();
			return true;
		} else
			return false;
	}
	
	public void setPause() {
		AudioPlayer.musicPause();
		setGameStatus(GameStatus.Pause);
	}
	
	public void setDestroy() {
		AudioPlayer.musicStop();
		setGameStatus(GameStatus.Destroy);
	}
	
	public void setRestart() {
		AudioPlayer.musicRestart();
		setGameStatus(GameStatus.Runing);
	}
	
	protected abstract boolean onInit();
	protected abstract void onPrepare();
	protected abstract void onExit();
	
	protected abstract boolean onBack();
	
	protected abstract void onPause();
	protected abstract void onDestroy();
	
	protected abstract void onEntryView(Canvas canvas, int deay);
	protected abstract void onSelectView(Canvas canvas, int deay);
	protected abstract void onStartingView(Canvas canvas, int deay);
	protected abstract void onDrawView(Canvas canvas);
	protected abstract void onEndingView(Canvas canvas, int deay);
	protected abstract boolean onLeaveView(Canvas canvas, int deay);
}
