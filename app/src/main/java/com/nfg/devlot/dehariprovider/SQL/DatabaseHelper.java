package com.nfg.devlot.dehariprovider.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nfg.devlot.dehariprovider.Models.JobModel;
import com.nfg.devlot.dehariprovider.Models.UserModel;
import com.nfg.devlot.dehariprovider.Session.UserSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassan on 3/24/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{


    private static int DATABASE_VERSION                     =1;
    private static String DATABASE_NAME                     ="dehari_db";

    // Table names.
    private static final String table_service_seeker         = "SERVICE_SEEKER";
    private static final String table_jobs                   = "JOBS";

    // Service seeker: column names.
    private static final String col_ss_id                    = "ID";
    private static final String col_ss_ss_id                 = "SS_ID";
    private static final String col_ss_name                  = "NAME";
    private static final String col_ss_email                 = "EMAIL";
    private static final String col_ss_phone_number          = "PHONE_NUMBER";
    private static final String col_ss_image_path            = "IMAGE_PATH";

    // Jobs: column names.
    private static final String col_job_id                   = "ID";
    private static final String col_job_job_id               = "J_ID";
    private static final String col_job_s_id                 = "SERVICE_ID";
    private static final String col_job_ss_id                = "SERVICE_SEEKER_ID";
    private static final String col_job_sp_id                = "SERVICE_PROVIDER_ID";
    private static final String col_job_start_time           = "START_TIME";
    private static final String col_job_ending_time          = "ENDING_TIME";
    private static final String col_job_location             = "LOCATION";
    private static final String col_job_status               = "STATUS";
    private static final String col_job_additional_charges   = "ADDITIONAL_CHARGES";


    // Queries: Table creation.
    private static final String create_ss_table = "CREATE TABLE " + table_service_seeker + "("
            + col_ss_id             + " TEXT,"
            + col_ss_ss_id          + " TEXT,"
            + col_ss_name           + " TEXT,"
            + col_ss_email          + " TEXT,"
            + col_ss_phone_number   + " TEXT,"
            + col_ss_image_path     + " TEXT)";


    private static final String create_job_table = "CREATE TABLE " + table_jobs + "("
            + col_job_id                    + " TEXT,"
            + col_job_job_id                + " TEXT,"
            + col_job_s_id                  + " TEXT,"
            + col_job_ss_id                 + " TEXT,"
            + col_job_sp_id                 + " TEXT,"
            + col_job_start_time            + " TEXT,"
            + col_job_ending_time           + " TEXT,"
            + col_job_location              + " TEXT,"
            + col_job_status                + " TEXT,"
            + col_job_additional_charges    + " TEXT)";




    public boolean insertUser(UserModel user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(col_ss_id, user.getSs_id());
        values.put(col_ss_name, user.getName());
        values.put(col_ss_email, user.getEmail());
        values.put(col_ss_phone_number, user.getPhone_number());
        values.put(col_ss_image_path, user.getImagePath());

        if (db.insert(table_service_seeker, null, values) > -1)
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }
    }

    public UserModel retrieveUser()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_service_seeker, new String[] {col_ss_id,
                                                                    col_ss_ss_id,
                                                                    col_ss_name,
                                                                    col_ss_email,
                                                                    col_ss_phone_number,
                                                                    col_ss_image_path},
                null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        UserModel user = new UserModel(String.valueOf(cursor.getInt(cursor.getColumnIndex(col_ss_id))),
                                        String.valueOf(cursor.getInt(cursor.getColumnIndex(col_ss_ss_id))),
                                        cursor.getString(cursor.getColumnIndex(col_ss_name)),
                                        cursor.getString(cursor.getColumnIndex(col_ss_email)),
                                        cursor.getString(cursor.getColumnIndex(col_ss_phone_number)),
                                        cursor.getString(cursor.getColumnIndex(col_ss_image_path)));

        cursor.close();

        return user;
    }

    public boolean checkUser()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_service_seeker, new String[] {col_ss_id,
                                                                    col_ss_ss_id,
                                                                    col_ss_name,
                                                                    col_ss_email,
                                                                    col_ss_phone_number,
                                                                    col_ss_image_path},
                null, null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();

            if(cursor.getCount() > 0)
            {
                int indexColumn     = cursor.getColumnIndex(col_ss_id);
                int nameColumn      = cursor.getColumnIndex(col_ss_name);
                int emailColumn     = cursor.getColumnIndex(col_ss_email);
                int phoneColumn     = cursor.getColumnIndex(col_ss_phone_number);
               // int imagePathColumn = cursor.getColumnIndex(col_ss_image_path);

                UserSession.uid       = cursor.getString(indexColumn);
                UserSession.uname     = cursor.getString(nameColumn);
                UserSession.uemail    = cursor.getString(emailColumn);
                UserSession.uPhone    = cursor.getString(phoneColumn);
                //UserSession.imagePath = cursor.getString(imagePathColumn);

                Log.d("test->LocalId",cursor.getString(indexColumn));
                Log.d("test->Localname",cursor.getString(nameColumn));
                Log.d("test->Localemail",cursor.getString(emailColumn));
                Log.d("test->Localphone",cursor.getString(phoneColumn));
//                Log.d("test->Localimagepath",cursor.getString(imagePathColumn));

                cursor.close();
                return true;
            }
        }

            cursor.close();
            return false;

    }

    public void deleteUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table_service_seeker, null, null);

        db.close();
    }

    public boolean insertJob(JobModel job)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_job_job_id, job.getJ_id());
        values.put(col_job_s_id, job.getS_id());
        values.put(col_job_ss_id, job.getSs_id());
        values.put(col_job_sp_id, job.getSp_id());
        values.put(col_job_start_time, job.getStarting_time());
        values.put(col_job_ending_time, job.getEnding_time());
        values.put(col_job_location, job.getLocation());
        values.put(col_job_status, job.getStatus());
        values.put(col_job_additional_charges, job.getAdditional_charges());

        if (db.insert(table_jobs, null, values) > -1)
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }
    }

    public List<JobModel> retrieveJob()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<JobModel> jobs = new ArrayList<>();

        Cursor cursor = db.query(table_jobs, new String[]{col_job_id,
                                                            col_job_s_id,
                                                            col_job_ss_id,
                                                            col_job_sp_id,
                                                            col_job_start_time,
                                                            col_job_ending_time,
                                                            col_job_location,
                                                            col_job_status,
                                                            col_job_additional_charges},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do
            {
                JobModel job = new JobModel(String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_id))),
                                                        String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_job_id))),
                                                        String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_s_id))),
                                                        String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_ss_id))),
                                                        String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_sp_id))),
                                                        cursor.getString(cursor.getColumnIndex(col_job_start_time)),
                                                        cursor.getString(cursor.getColumnIndex(col_job_ending_time)),
                                                        cursor.getString(cursor.getColumnIndex(col_job_location)),
                                                        cursor.getString(cursor.getColumnIndex(col_job_status)),
                                                        cursor.getString(cursor.getColumnIndex(col_job_additional_charges)));

                jobs.add(job);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return jobs;
    }

    public JobModel retrieveJob(int j_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_jobs, new String[]{col_job_id,
                                                            col_job_s_id,
                                                            col_job_ss_id,
                                                            col_job_sp_id,
                                                            col_job_start_time,
                                                            col_job_ending_time,
                                                            col_job_location,
                                                            col_job_status,
                                                            col_job_additional_charges},
                col_job_job_id + "=?", new String[] {String.valueOf(j_id)},
                null, null, null, null);

        if (cursor.moveToFirst()) {
            JobModel job = new JobModel(String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_id))),
                                                    String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_job_id))),
                                                    String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_s_id))),
                                                    String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_ss_id))),
                                                    String.valueOf(cursor.getInt(cursor.getColumnIndex(col_job_sp_id))),
                                                    cursor.getString(cursor.getColumnIndex(col_job_start_time)),
                                                    cursor.getString(cursor.getColumnIndex(col_job_ending_time)),
                                                    cursor.getString(cursor.getColumnIndex(col_job_location)),
                                                    cursor.getString(cursor.getColumnIndex(col_job_status)),
                                                    cursor.getString(cursor.getColumnIndex(col_job_additional_charges)));

            cursor.close();
            return job;
        }
        else
        {
            cursor.close();
            return null;
        }
    }

    public void deleteJob(int j_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table_jobs, col_job_job_id + "=?", new String[] {String.valueOf(j_id)});

        db.close();
    }


    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create_ss_table);
        db.execSQL(create_job_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TBD
    }
}
