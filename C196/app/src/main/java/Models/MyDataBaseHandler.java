package Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class MyDataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "schedulerDataBase.db";

    // Table columns for Assessment Table
    private static final String TABLE_ASSESSMENT_NAME = "Assessments";
    private static final String TABLE_ASSESSMENT_ID = "id";
    private static final String TABLE_ASSESSMENT_TITLE = "AssessmentsTitle";
    private static final String TABLE_ASSESSMENT_DATE = "AssessmentsDate";
    private static final String TABLE_ASSESSMENT_CATEGORY = "AssessmentsCategory";
    private static final String TABLE_ASSESSMENT_COURSE_ID = "AssessmentsCourseID";
    private static final String TABLE_ASSESSMENT_DESCRIPTION = "AssessmentsDescription";

    // Table columns for Course Table
    private static final String TABLE_COURSE_NAME = "Course";
    private static final String TABLE_COURSE_ID = "id";
    private static final String TABLE_COURSE_TITLE = "CourseTitle";
    private static final String TABLE_COURSE_START_DATE = "CourseStartDate";
    private static final String TABLE_COURSE_END_DATE = "CourseEndDate";
    private static final String TABLE_COURSE_TERM_ID = "CourseTermID";
    private static final String TABLE_COURSE_STATUS = "CourseStatus";

    // Table columns for Notes Table
    private static final String TABLE_NOTE_NAME = "Notes";
    private static final String TABLE_NOTE_ID = "id";
    private static final String TABLE_NOTE_DESCRIPTION = "NoteDescription";
    private static final String TABLE_NOTE_COURSE_ID = "NoteCourseID";

    // Table columns for Teacher Table
    private static final String TABLE_TEACHER_NAME = "Teacher";
    private static final String TABLE_TEACHER_ID = "id";
    private static final String TABLE_TEACHER_PERSON_NAME = "TeachersName";
    private static final String TABLE_TEACHER_PHONE_NUMBER = "TeacherPhoneNumber";
    private static final String TABLE_TEACHER_EMAIL_ADDRESS = "TeacherEmailAddress";
    private static final String TABLE_TEACHER_COURSE_ID = "TeacherCourseID";

    // Table columns for Term Table
    private static final String TABLE_TERM_NAME = "Term";
    private static final String TABLE_TERM_ID = "id";
    private static final String TABLE_TERM_TITLE = "TermTitle";
    private static final String TABLE_TERM_START_DATE = "TermStartDate";
    private static final String TABLE_TERM_END_DATE = "endDate";



    public MyDataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TERM_TABLE = "CREATE TABLE " + TABLE_TERM_NAME + "("
                + TABLE_TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_TERM_TITLE + " TEXT,"
                + TABLE_TERM_START_DATE + " TEXT," +  TABLE_TERM_END_DATE + " TEXT" + ")";

        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE_NAME + "("
                + TABLE_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_COURSE_TITLE + " TEXT,"
                + TABLE_COURSE_START_DATE + " TEXT," +  TABLE_COURSE_END_DATE + " TEXT," + TABLE_COURSE_STATUS + " TEXT," +
                TABLE_COURSE_TERM_ID + " TEXT" + ")";

        String CREATE_TEACHER_TABLE = "CREATE TABLE " + TABLE_TEACHER_NAME + "("
                + TABLE_TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_TEACHER_PERSON_NAME + " TEXT,"
                + TABLE_TEACHER_PHONE_NUMBER + " TEXT," +  TABLE_TEACHER_EMAIL_ADDRESS + " TEXT," +
                TABLE_TEACHER_COURSE_ID + " INTEGER" + ")";

        String CREATE_ASSESMENT_TABLE = "CREATE TABLE " + TABLE_ASSESSMENT_NAME + "("
                + TABLE_ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_ASSESSMENT_DESCRIPTION + " TEXT,"
                + TABLE_ASSESSMENT_COURSE_ID + " INTEGER," + TABLE_ASSESSMENT_CATEGORY + " TEXT," + TABLE_ASSESSMENT_TITLE
                + " TEXT, " + TABLE_ASSESSMENT_DATE + " TEXT" + ")";

        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTE_NAME + "("
                + TABLE_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_NOTE_DESCRIPTION + " TEXT,"
                + TABLE_NOTE_COURSE_ID + " INTEGER" + ")";


        db.execSQL(CREATE_TERM_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_TEACHER_TABLE);
        db.execSQL(CREATE_ASSESMENT_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE_NAME);
        onCreate(db);
    }



    public void addTerm(Term term){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_TERM_TITLE, term.getTermTitle());
        values.put(TABLE_TERM_START_DATE, term.getStartDate());
        values.put(TABLE_TERM_END_DATE, term.getEndDate());
        sqLiteDatabase.insert(TABLE_TERM_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void addCourse(Course course){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_COURSE_TITLE, course.getName());
        values.put(TABLE_COURSE_START_DATE, course.getStartDate());
        values.put(TABLE_COURSE_END_DATE, course.getEndDate());
        values.put(TABLE_COURSE_STATUS, course.getCourseStatus());
        values.put(TABLE_COURSE_TERM_ID, course.getCourseTerm());
        sqLiteDatabase.insert(TABLE_COURSE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void addTeacher(Teacher teacher){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_TEACHER_PERSON_NAME, teacher.getTeacherPersonName());
        values.put(TABLE_TEACHER_PHONE_NUMBER, teacher.getPhoneNumber());
        values.put(TABLE_TEACHER_EMAIL_ADDRESS, teacher.getEmailAddress());
        values.put(TABLE_TEACHER_COURSE_ID, teacher.getCourseID());
        sqLiteDatabase.insert(TABLE_TEACHER_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void addAssesment(Assessment assessment){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ASSESSMENT_DESCRIPTION, assessment.getDescription());
        values.put(TABLE_ASSESSMENT_COURSE_ID, assessment.getAssessmentCourse());
        values.put(TABLE_ASSESSMENT_CATEGORY, assessment.getCategory());
        values.put(TABLE_ASSESSMENT_TITLE, assessment.getTitle());
        values.put(TABLE_ASSESSMENT_DATE, assessment.getDate());
        sqLiteDatabase.insert(TABLE_ASSESSMENT_NAME, null, values);
        sqLiteDatabase.close();
    }
    public void addNote(Note note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_NOTE_DESCRIPTION, note.getDesc());
        values.put(TABLE_NOTE_COURSE_ID, note.getCourseID());
        sqLiteDatabase.insert(TABLE_NOTE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void updateCourse(int id, Course course){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_COURSE_TITLE, course.getName());
        values.put(TABLE_COURSE_START_DATE, course.getStartDate());
        values.put(TABLE_COURSE_END_DATE, course.getEndDate());
        values.put(TABLE_COURSE_STATUS, course.getCourseStatus());
        values.put(TABLE_COURSE_TERM_ID, course.getCourseTerm());

        db.update(TABLE_COURSE_NAME, values, TABLE_COURSE_ID + " = ?" ,
                new String[] { String.valueOf(id) });
        db.close();
    }


    public void updateAssessment(int id, Assessment assessment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ASSESSMENT_DESCRIPTION, assessment.getDescription());
        values.put(TABLE_ASSESSMENT_COURSE_ID, assessment.getAssessmentCourse());
        values.put(TABLE_ASSESSMENT_CATEGORY, assessment.getCategory());
        values.put(TABLE_ASSESSMENT_TITLE, assessment.getTitle());
        values.put(TABLE_ASSESSMENT_DATE, assessment.getDate());
        db.update(TABLE_ASSESSMENT_NAME, values, TABLE_ASSESSMENT_ID + " = ?" ,
                new String[] { String.valueOf(id) });
        db.close();
    }

    public ArrayList<Term> getAllTerms(){
        ArrayList<Term> termsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TERM_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Term term = new Term();
                term.setId(Integer.parseInt(cursor.getString(0)));
                term.setTermTitle(cursor.getString(1));
                term.setStartDate(cursor.getString(2));
                term.setEndDate(cursor.getString(3));
                termsList.add(term);
            }while (cursor.moveToNext());
        }

        return termsList;
    }
    public int getNoteForACourseCount(int courseID) {
        ArrayList<Note> notesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NOTE_NAME, new String[] { TABLE_NOTE_ID,
                        TABLE_NOTE_DESCRIPTION, TABLE_NOTE_COURSE_ID}, TABLE_NOTE_COURSE_ID + "=?",
                new String[] { String.valueOf(courseID) }, null, null, null, null);
        return cursor.getCount();
    }

    public ArrayList<Note> getNote(int courseID) {
        ArrayList<Note> notesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NOTE_NAME, new String[] { TABLE_NOTE_ID,
                        TABLE_NOTE_DESCRIPTION, TABLE_NOTE_COURSE_ID}, TABLE_NOTE_COURSE_ID + "=?",
                new String[] { String.valueOf(courseID) }, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setDesc(cursor.getString(1));
                note.setCourseID(cursor.getInt(2));
                notesList.add(note);
            }while (cursor.moveToNext());
        }

        return notesList;
    }
    public int getAssesmentForACourseCount(int courseID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_ASSESSMENT_NAME, new String[] { TABLE_ASSESSMENT_ID,
                        TABLE_ASSESSMENT_DESCRIPTION, TABLE_ASSESSMENT_COURSE_ID, TABLE_ASSESSMENT_CATEGORY, TABLE_ASSESSMENT_TITLE, TABLE_ASSESSMENT_DATE}, TABLE_ASSESSMENT_COURSE_ID + " = ?",
                new String[] { String.valueOf(courseID) }, null, null, null, null);
        return cursor.getCount();
    }
    public ArrayList<Assessment> getAssesment(int courseID){
        ArrayList<Assessment> assesmentsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_ASSESSMENT_NAME, new String[] { TABLE_ASSESSMENT_ID,
                        TABLE_ASSESSMENT_DESCRIPTION, TABLE_ASSESSMENT_COURSE_ID, TABLE_ASSESSMENT_CATEGORY, TABLE_ASSESSMENT_TITLE, TABLE_ASSESSMENT_DATE}, TABLE_ASSESSMENT_COURSE_ID + " = ?",
                new String[] { String.valueOf(courseID) }, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Assessment assessment = new Assessment();
                assessment.setId(Integer.parseInt(cursor.getString(0)));
                assessment.setDescription(cursor.getString(1));
                assessment.setAssessmentCourse(cursor.getInt(2));
                assessment.setCategory(cursor.getString(3));
                assessment.setTitle(cursor.getString(4));
                assessment.setDate(cursor.getString(5));
                assesmentsList.add(assessment);
            }while (cursor.moveToNext());
        }

        return assesmentsList;
    }

    public int getCoursesCountForATerm(String termName){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_COURSE_NAME, new String[] { TABLE_COURSE_ID,
                        TABLE_COURSE_TITLE, TABLE_COURSE_START_DATE, TABLE_COURSE_END_DATE,
                        TABLE_COURSE_STATUS, TABLE_COURSE_TERM_ID }, TABLE_COURSE_TERM_ID + "=?",
                new String[] { String.valueOf(termName) }, null, null, null, null);
        return cursor.getCount();
    }

    public void deleteTerm(Term term){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_TERM_NAME, TABLE_TERM_ID + " = ?",
                new String[] { String.valueOf(term.getId()) });
        sqLiteDatabase.close();
    }

    public void deleteAssessment(Assessment assessment){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_ASSESSMENT_NAME, TABLE_ASSESSMENT_ID + " = ?",
                new String[] { String.valueOf(assessment.getId()) });
        sqLiteDatabase.close();
    }

    public ArrayList<Course> getCoursesForATerm(String termName) {
        ArrayList<Course> listOfCourses = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_COURSE_NAME,new String[] {
                TABLE_COURSE_ID, TABLE_COURSE_TITLE, TABLE_COURSE_START_DATE, TABLE_COURSE_END_DATE,
                TABLE_COURSE_STATUS, TABLE_COURSE_TERM_ID
        }, TABLE_COURSE_TERM_ID  + "=?" , new String[]{ String.valueOf(termName)}, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Course course = new Course();
                course.setId(Integer.parseInt(cursor.getString(0)));
                course.setName(cursor.getString(1));
                course.setStartDate(cursor.getString(2));
                course.setEndDate(cursor.getString(3));
                listOfCourses.add(course);
            }while (cursor.moveToNext());
        }

        return listOfCourses;
    }



    public ArrayList<Course> getAllCourses(){
        ArrayList<Course> coursesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Course course = new Course();
                course.setId(Integer.parseInt(cursor.getString(0)));
                course.setName(cursor.getString(1));
                course.setStartDate(cursor.getString(2));
                course.setEndDate(cursor.getString(3));
                course.setCourseStatus(cursor.getString(4));
                course.setCourseTerm(cursor.getString(5));
                coursesList.add(course);
            }while (cursor.moveToNext());
        }

        return coursesList;
    }

    public void deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE_NAME, TABLE_COURSE_ID + " = ?",
                new String[] { String.valueOf(course.getId()) });
        db.close();
    }

    public ArrayList<Teacher> getAllTeachers(){
        ArrayList<Teacher> teachersList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TEACHER_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Teacher teacher = new Teacher();
                teacher.setId(Integer.parseInt(cursor.getString(0)));
                teacher.setTeacherPersonName(cursor.getString(1));
                teacher.setPhoneNumber(cursor.getString(2));
                teacher.setEmailAddress(cursor.getString(3));
                teachersList.add(teacher);
            }while (cursor.moveToNext());
        }

        return teachersList;
    }

    public int getTeachersForACourseCount(int courseID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_TEACHER_NAME, new String[] { TABLE_TEACHER_ID,
                        TABLE_TEACHER_PERSON_NAME, TABLE_TEACHER_PHONE_NUMBER, TABLE_TEACHER_EMAIL_ADDRESS}, TABLE_TEACHER_COURSE_ID + "=?",
                new String[] { String.valueOf(courseID) }, null, null, null, null);
        return cursor.getCount();
    }
    public ArrayList<Teacher> getTeachersForACourse(int courseID){
        ArrayList<Teacher> teacherList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_TEACHER_NAME, new String[] { TABLE_TEACHER_ID,
                        TABLE_TEACHER_PERSON_NAME, TABLE_TEACHER_PHONE_NUMBER, TABLE_TEACHER_EMAIL_ADDRESS}, TABLE_TEACHER_COURSE_ID + "=?",
                new String[] { String.valueOf(courseID) }, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Teacher teacher = new Teacher();
                teacher.setId(Integer.parseInt(cursor.getString(0)));
                teacher.setTeacherPersonName(cursor.getString(1));
                teacher.setPhoneNumber(cursor.getString(2));
                teacher.setEmailAddress(cursor.getString(3));
                teacherList.add(teacher);
            }while (cursor.moveToNext());
        }

        return teacherList;
    }

}
