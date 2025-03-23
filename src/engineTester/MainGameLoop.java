package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrain.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();

		Loader loader = new Loader();

		List<Entity> entities = new ArrayList<Entity>();

		RawModel model = OBJLoader.loadObjModel("dragon2", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("blank")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1.0f);

		for (int i = 0; i < 10; i++) {
			entities.add(new Entity(staticModel,
					new Vector3f((float) (Math.random() * 100 - 50), (float) (Math.random() * 100 - 50),
							(float) (Math.random() * 100 - 50)),
					(float) (Math.random() * 360), (float) (Math.random() * 360), (float) (Math.random() * 360),
					(float) (Math.random() + 1)));
		}

		RawModel sun = OBJLoader.loadObjModel("sphere", loader);
		ModelTexture sunTexture = new ModelTexture(loader.loadTexture("sun"));
		sunTexture.setReflectivity(1.0f);
		sunTexture.setShineDamper(10);
		
		RawModel grass = OBJLoader.loadObjModel("grassModel", loader);
		ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
		grassTexture.setTransparency(true);
		grassTexture.setReflectivity(1.0f);
		grassTexture.setShineDamper(10);
		
		entities.add(new Entity(new TexturedModel(grass, grassTexture), new Vector3f(100, 0, 100), 0, 0, 0, 1));

		RawModel tree = OBJLoader.loadObjModel("lowPolyTree", loader);
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("lowPolyTree"));
		treeTexture.setReflectivity(1.0f);
		treeTexture.setShineDamper(10);
		
		entities.add(new Entity(new TexturedModel(tree, treeTexture), new Vector3f(200, 0, 100), 0, 0, 0, 1));
		
		RawModel tree2 = OBJLoader.loadObjModel("tree", loader);
		ModelTexture tree2Tex = new ModelTexture(loader.loadTexture("tree"));
		tree2Tex.setReflectivity(1.0f);
		tree2Tex.setShineDamper(10);
		
		entities.add(new Entity(new TexturedModel(tree2, tree2Tex), new Vector3f(300, 0 , 200), 0, 0, 0, 1));
		
		RawModel drone = OBJLoader.loadObjModel("drone", loader);
		entities.add(new Entity(new TexturedModel(drone, texture), new Vector3f(100, 300 , 200), 0, 0, 0, 1));
		
		float theta = 0;

		Vector3f lightPosition = new Vector3f((float) (2000 * Math.cos(theta)), 2000, (float) (2000 * Math.sin(theta)));

		Entity sunEntity = new Entity(new TexturedModel(sun, sunTexture), lightPosition, 0, 0, 0, 500);

		RawModel cube = OBJLoader.loadObjModel("cube", loader);
		ModelTexture cubeTexture = new ModelTexture(loader.loadTexture("cube"));
		cubeTexture.setShineDamper(10);
		cubeTexture.setReflectivity(0.5f);
		TexturedModel cubeModel = new TexturedModel(cube, cubeTexture);
		for (int i = 0; i < 100; i++) {
			entities.add(new Entity(cubeModel,
					new Vector3f((float) (Math.random() * 100 - 50), (float) (Math.random() * 100 - 50),
							(float) (Math.random() * 100 - 50)),
					(float) (Math.random() * 360), (float) (Math.random() * 360), (float) (Math.random() * 360), 1));
		}

		Light light = new Light(lightPosition, new Vector3f(1f, 1f, 1f));

		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("grass"));
		terrainTexture.setShineDamper(10);
		terrainTexture.setReflectivity(0.8f);
		Terrain terrain = new Terrain(0, 0, loader, terrainTexture);
		Terrain terrain2 = new Terrain(1, 0, loader, terrainTexture);

		Camera camera = new Camera();

		MasterRenderer renderer = new MasterRenderer();

		while (!Display.isCloseRequested()) {
			theta += 0.01f;
			light.setPosition(new Vector3f((float) (2000 * Math.cos(theta)), 2000, (float) (2000 * Math.sin(theta))));
			sunEntity.setPosition(light.getPosition());
			camera.move();

			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);

			for (Entity e : entities) {
				renderer.processEntity(e);
				renderer.processEntity(sunEntity);
			}

			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
	}

}
