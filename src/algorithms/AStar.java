package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import gui.GSRunning;
import map.Map;

/**
 * 
 * @author Rui Z.
 * @version August 29, 2015
 * @about imported from an older project of mine & modified to work with T3D
 */
public class AStar {
	public static Map world;
	static Node[][] graph;
	static PriorityQueue<Node> openList = new PriorityQueue<Node>();
	static ArrayList<Node> closedList = new ArrayList<Node>();

	public static void setWorld(Map m) {
		world = m;
		graph = new Node[world.getY()][world.getX()];
		for (int i = 0; i < world.getY(); i++)
			for (int j = 0; j < world.getX(); j++)
				graph[i][j] = new Node(j, i);
	}

	public static Stack<Point> findPath(int enemyX, int enemyY, int targetX, int targetY) {
		Stack<Point> path = new Stack<Point>();
		openList.clear();
		closedList.clear();
		Node start = graph[enemyY][enemyX];
		Node current = start;
		openList.add(start);
		while (!openList.isEmpty()) {
			current = openList.peek();
			openList.remove();
			closedList.add(current);
			if (current.locationX == targetX && current.locationY == targetY) {
				while (current != start) {
					path.add(new Point(current.locationX * GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2,
							current.locationY * GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2));
					current = current.prev;
				}
				break;
			}
			for (int i = -1; i < 2; i++)
				for (int j = -1; j < 2; j++) {
					// out of bounds
					if (current.locationY + i < 0 || current.locationX + j < 0
							|| current.locationY + i > world.getY() - 1 || current.locationX + j > world.getX() - 1)
						continue;
					// direction headed is blocked
					if (world.getTile(current.locationX + j, current.locationY + i).isSolid())
						continue;

					// skips case of itself
					if (i == 0 && j == 0)
						continue;

					Node nextNode = graph[current.locationY + i][current.locationX + j];
					int add = 10;
					// diagonal case
					if (i != 0 && j != 0) {
						// sqrt(10^2+10^2)
						add = 14;
						// if one of the two collisions is blocked then skip
						// this case
						if (world.getTile(current.locationX, current.locationY + i).isSolid()
								|| world.getTile(current.locationX + j, current.locationY).isSolid())
							continue;
					}
					// in closed List, then skip
					if (closedList.contains(nextNode))
						continue;
					int g = 0;
					if (current.prev != null) {
						g = current.g;
					}
					g += add;
					// in open List, then update
					if (openList.contains(nextNode)) {
						// if old g is greater than new g, swap it out
						if (nextNode.g > g) {
							nextNode.g = g;
							nextNode.setParent(current);
							;
						}
						// record new node
					} else {
						nextNode.setParent(current);
						nextNode.g = g;
						// weighted so that algorithm prefers movement
						nextNode.h = (Math.abs(nextNode.locationX - targetX) + Math.abs(nextNode.locationY - targetY))
								* 10.001;
						openList.add(nextNode);
					}
				}
		}
		return path;
	}

	private static class Node implements Comparable<Node> {
		int locationX;
		int locationY;
		Node prev;
		int g;
		double h;

		public Node(int locationX, int locationY) {
			this.locationX = locationX;
			this.locationY = locationY;
		}

		public void setParent(Node p) {
			prev = p;
		}

		// implemented to use priorityQueue's comparisons
		public int compareTo(Node b) {
			double af = g + h;
			double bf = b.g + b.h;
			if (af < bf) {
				return -1;
			} else if (af > bf) {
				return 1;
			} else {
				return 0;
			}
		}

	}

}
