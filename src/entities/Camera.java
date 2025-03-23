package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float speed;
	
	public Camera() {
		speed = 0.2f;
	}
	
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			speed = 2.0f;
		} else {
			speed = 0.2f;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += speed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			pitch += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			pitch -= speed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			yaw -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			yaw += speed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			roll -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			roll += speed;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

}
