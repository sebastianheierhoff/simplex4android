package com.googlecode.simplex4android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class ObjectSerializer {
	private FileOutputStream FOS = null;
	private ObjectOutputStream OOS = null;
	private FileInputStream FIS = null;
	private ObjectInputStream OIS = null;

	public ObjectSerializer() {
	}

	public void save2file(Object obj, File file) {
		if (OOS == null || FOS == null) {
			open_out(file);
		}

		try {
			OOS.writeObject(obj);
		} catch (IOException ioe) {
			System.err.println
			("Error: Could not serialize object.");
			ioe.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public Object readFromFile(File file) {
		if (OIS == null || FIS == null) {
			open_in(file);
		}

		try {
			Object obj = (Object) OIS.readObject();
			return obj;
		} catch (IOException ioe) {
			System.err.println
			("Error: Could not deserialize object.");
			ioe.printStackTrace(System.err);
			System.exit(1);
		} catch (ClassNotFoundException cnfe) {
			System.err.println
			("Error: Could not find class!");
			cnfe.printStackTrace(System.err);
			System.exit(1);
		}
		return null;
	}

	private void open_out(File file) {
		if (OOS != null || FOS != null) {
			close_out();
		}

		try {
			FOS = new FileOutputStream(file);
			OOS = new ObjectOutputStream(FOS);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			ioe.printStackTrace(System.out);
			System.exit(1);
		}
	}

	private void open_in(File file) {
		if (FIS != null || OIS != null) {
			close_in();
		}

		try {
			FIS = new FileInputStream(file);
			OIS = new ObjectInputStream(FIS);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			ioe.printStackTrace(System.out);
			System.exit(1);
		}
	}

	public void close_out() {
		if (OOS != null && FOS != null) {
			try {
				OOS.close();
				OOS = null;
				FOS.close();
				FOS = null;
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
				ioe.printStackTrace(System.out);
				System.exit(1);
			}
		}

	}

	public void close_in() {
		if (OIS != null && FIS != null) {
			try {
				OIS.close();
				OIS = null;
				FIS.close();
				FIS = null;
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
				ioe.printStackTrace(System.out);
				System.exit(1);
			}
		}

	}

	public static void main(String[] args) {
		File file = new File("arraysasfile.bin");
		// arrays definieren
		String[] str_arr = new String[] { "Hallo", "Du", "da" };
		int[] int_arr = new int[] { 1, 2, 3, 4 };
		// serializer erzeugen
		ObjectSerializer serializer = new ObjectSerializer();
		// arrays speichern
		serializer.save2file(str_arr, file);
		serializer.save2file(int_arr, file);
		serializer.close_out();
		// arrays laden
		String[] str_arr_read =
			(String[]) serializer.readFromFile(file);
		int[] int_arr_read =
			(int[]) serializer.readFromFile(file);
		serializer.close_in();
		// geladene arrays ausgeben
		System.out.println(Arrays.toString(str_arr_read));
		System.out.println(Arrays.toString(int_arr_read));

		System.exit(0);
	}
}