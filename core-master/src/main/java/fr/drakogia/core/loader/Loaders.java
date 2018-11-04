package fr.drakogia.core.loader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import fr.drakogia.api.loader.ILoader;
import fr.drakogia.core.DrakogiaApi;

public class Loaders implements ILoader {

	private static Loaders instance;

	public static Loaders getInstance() {
		if (instance == null) {
			instance = new Loaders();
		}

		return instance;
	}

	public void loadLibraries(boolean defsToo) throws IOException {
		final ArrayList<File> files = new ArrayList<File>();

		for (final File file : getNativesFolder().listFiles()) {
			if ((file != null) && file.getName().toLowerCase().endsWith("jar")) {
				files.add(file);
			}
		}

		loadLibraries(files);
	}

	public void loadLibraries(List<File> files) throws IOException {
		for (final File file : files) {
			if (file.getName().toLowerCase().endsWith("jar")) {
				addJar(getURL(file));
				System.out.println("[Loaders] => " + file.getName() + " loaded");
			}
		}
	}

	public File getNativesFolder() {
		final File f = new File(getServerFolder(), "loaders");
		if (!f.exists()) {
			f.mkdirs();
		}

		return f;
	}

	public File getServerFolder() {
		try {
			return new File(DrakogiaApi.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getParentFile();
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public URL getURL(File javaArchive) throws IOException {
		return new URL("jar:" + javaArchive.toURI().toURL().toExternalForm() + "!/");
	}

	public void addJar(URL jarURL) throws IOException {
		final URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		final Class<URLClassLoader> sysclass = URLClassLoader.class;

		try {
			final Method method = sysclass.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(loader, new Object[] { jarURL });
		} catch (final Throwable t) {
			t.printStackTrace();
		}
	}

}
