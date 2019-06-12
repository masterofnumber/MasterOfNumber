package com.mon.masterofnumber;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.games.Games;

public final class MasterOfNumberServices {

    private GoogleSignInClient mGoogleSingnInClient;
    private LeaderboardsClient mLeaderboardClient;

    private static final int RC_SIGN_IN = 9001;
    private final int RC_UNUSED = 9002;
    private AppCompatActivity mactivity;
    private PlayersClient mPlayersClient;
    private String mplayerName;


    public void onCreateActivity(AppCompatActivity activity)
    {
        mactivity = activity;
        mGoogleSingnInClient = GoogleSignIn.getClient(mactivity,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());

    }


    public boolean isSignedIn()
    {
        return GoogleSignIn.getLastSignedInAccount(mactivity) != null;
    }

    public void signInSilently()
    {
        mGoogleSingnInClient.silentSignIn().addOnCompleteListener(mactivity,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete (@NonNull Task<GoogleSignInAccount> task) {

                        if (task.isSuccessful())
                            onConnected(task.getResult());
                        else
                            onDisconnected();
                    }
                });
    }


    private void onConnected(GoogleSignInAccount account)
    {
        mLeaderboardClient = Games.getLeaderboardsClient(mactivity, account);
        mPlayersClient = Games.getPlayersClient(mactivity, account);

        mPlayersClient.getCurrentPlayer()
                .addOnCompleteListener(new OnCompleteListener<Player>() {
                    @Override
                    public void onComplete (@NonNull Task<Player> task) {

                        if ( task.isSuccessful())
                            mplayerName = task.getResult().getDisplayName();
                    }
                });

    }

    public void UpdateScore(int score)
    {
        mLeaderboardClient.submitScore(mactivity.getString(R.string.leaderboard_masterofnumberrecord), score);
    }

    private void onDisconnected()
    {

        mLeaderboardClient = null;
        mPlayersClient = null;
    }

    private void startSignInIntent() {
        mactivity.startActivityForResult(mGoogleSingnInClient.getSignInIntent(), RC_SIGN_IN);

    }

    public void onResume()
    {
        signInSilently();
    }

    public void signOut()
    {
        if (!isSignedIn())
            return;

/*        mGoogleSingnInClient.signOut().addOnCompleteListener(
                new OnCompleteListener<Void>() {

                    @override
                    public void onComplete(@NonNull Task<Void> task) {

                        onDisconnected();
                    }

                });*/

        mGoogleSingnInClient.signOut().addOnCompleteListener(mactivity,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        onDisconnected();
                    }
                });
    }


    public void showLeaderboard() {

        mLeaderboardClient.getAllLeaderboardsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess (Intent intent) {
                        mactivity.startActivityForResult(intent, RC_UNUSED);
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if ( requestCode== RC_SIGN_IN) {
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(intent);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                onConnected(account);
            } catch( ApiException apiException) {

                String message = apiException.getMessage();
                if ( message==null || message.isEmpty())
                    message = mactivity.getString(R.string.signin_other_error);
                onDisconnected();
            }
        }
    }
}
