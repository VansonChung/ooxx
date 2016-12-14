package com.example.tic_tac_toe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private static final int PLAYER_NULL = -1, PLAYER_RED = 0, PLAYER_YELLOW = 1;

	private int mPlayer = PLAYER_RED;

	private static final int[][] VICTORY_GRID = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 },
	        { 2, 4, 6 } };

	private int[] mGridOwner = { PLAYER_NULL, PLAYER_NULL, PLAYER_NULL, PLAYER_NULL, PLAYER_NULL, PLAYER_NULL, PLAYER_NULL, PLAYER_NULL,
	        PLAYER_NULL };

	List<Map<String, Object>> mGridViewItmeList = new ArrayList<Map<String, Object>>();
	SimpleAdapter mSimpleAdapter;

	GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGridView = (GridView) findViewById(R.id.gridView1);
		mGridView.setNumColumns(3);

		for (int i = 0; i < 9; i++) {
			Map<String, Object> gridViewListItem = new HashMap<String, Object>();
			gridViewListItem.put("image", null);
			mGridViewItmeList.add(gridViewListItem);
		}

		mSimpleAdapter = new SimpleAdapter(this, mGridViewItmeList, R.layout.gridview, new String[] { "image" }, new int[] { R.id.imageView1 });
		mGridView.setAdapter(mSimpleAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (mGridOwner[position] != PLAYER_NULL) // have owner ?
					return;
				mGridOwner[position] = mPlayer;
				RelativeLayout relativeLayout = (RelativeLayout) view;
				ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.imageView1);
				imageView.setImageResource(mPlayer == PLAYER_RED ? R.drawable.red : R.drawable.yellow);

				for (int i = 0; i < VICTORY_GRID.length; i++) { // win?
					if (mGridOwner[VICTORY_GRID[i][0]] == mGridOwner[VICTORY_GRID[i][1]]
		                    && mGridOwner[VICTORY_GRID[i][1]] == mGridOwner[VICTORY_GRID[i][2]] && mGridOwner[VICTORY_GRID[i][0]] != PLAYER_NULL) {
						winner();
						return;
					}
				}

				boolean isDraw = true; // draw?
				for (int i = 0; i < mGridOwner.length; i++) {
					if (mGridOwner[i] == PLAYER_NULL)
						isDraw = false;
				}
				if (isDraw)
					draw();

				if (mPlayer == PLAYER_RED)
					mPlayer = PLAYER_YELLOW;
				else
					mPlayer = PLAYER_RED;
			}
		});
	}

	private void winner() {
		setDialog("Winner : " + (mPlayer == PLAYER_RED ? "RED" : "YELLOW"));
	}

	private void draw() {
		setDialog("DRAW !");
	}

	private void setDialog(String title) {
		Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle(title);
		builder.setNegativeButton("Play again", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mGridView.setAdapter(mSimpleAdapter);
				for (int i = 0; i < mGridOwner.length; i++) {
					mGridOwner[i] = PLAYER_NULL;
				}
			}
		});
		builder.setPositiveButton("Exit", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}
}
