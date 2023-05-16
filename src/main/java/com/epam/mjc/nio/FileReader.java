package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileReader {

	private static Logger log = Logger.getLogger(FileReader.class.getName());

	public Profile getDataFromFile(File file) {

		try (RandomAccessFile iF = new RandomAccessFile(file, "rw")) {
			int bytes;
			StringBuilder sb = new StringBuilder();
			FileChannel inChanel = iF.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
			List<String> ls = new ArrayList<>();

			bytes = inChanel.read(byteBuffer);

			while (bytes != -1) {
				byteBuffer.flip();
				while (byteBuffer.hasRemaining()) {
					sb.append((char) byteBuffer.get());
				}
				byteBuffer.clear();
				bytes = inChanel.read(byteBuffer);
			}

			String res = sb.toString();
			res = res.trim();
			String[] arr = res.split("\\n");
			for (String str : arr) {
				ls.add(str.substring(str.indexOf(":") + 1).trim());
			}

			return new Profile(ls.get(0), Integer.parseInt(ls.get(1)), ls.get(2), Long.parseLong(ls.get(3)));



		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}


		return new Profile();
	}
}
