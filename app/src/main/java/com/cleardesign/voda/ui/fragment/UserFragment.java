package com.cleardesign.voda.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cleardesign.voda.R;
import com.cleardesign.voda.model.pojo.user.User;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private View myFragmentView;
    final String FILE_NAME = "user";

    EditText userName;
    EditText userEmail;
    EditText userAddress;
    EditText userPhone;


    private OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }


    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_user, container, false);

        final User user = User.getInstance();
        user.readUserFromFile(getActivity().getBaseContext());

        File file = getActivity().getBaseContext().getFileStreamPath(FILE_NAME);
        userName = (EditText) myFragmentView.findViewById(R.id.userName);
        userEmail = (EditText) myFragmentView.findViewById(R.id.userEmail);
        userAddress = (EditText) myFragmentView.findViewById(R.id.userAddress);
        userPhone = (EditText) myFragmentView.findViewById(R.id.userPhone);
        if (file.exists()) {
            userName.setText(user.getFio());
            userEmail.setText(user.getEmail());
            userAddress.setText(user.getAddress());
            userPhone.setText(user.getPhone());
        }

        Button btSaveUser = (Button) myFragmentView.findViewById(R.id.btSaveUser);
        btSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userName.getText().toString().equals("")  && !userEmail.getText().toString().equals("") &&
                        !userAddress.getText().toString().equals("") && !userPhone.getText().toString().equals("")) {
                    user.setFio(userName.getText().toString());
                    user.setEmail(userEmail.getText().toString());
                    user.setAddress(userAddress.getText().toString());
                    user.setPhone(userPhone.getText().toString());
                    user.writeUserToFile(getActivity().getBaseContext(), getActivity());
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Изменения сохранены", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        return myFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
