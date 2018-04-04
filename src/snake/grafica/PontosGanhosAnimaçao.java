package snake.grafica;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import snake.principal.Cell;
import snake.principal.Principal;

@SuppressWarnings("serial")
public class PontosGanhosAnima�ao extends JPanel implements Runnable{
	public int tempo = 50;
	public String valor;
	public float alpha = 1;
	public int xPosi�ao;
	public int yPosi�ao;
	private boolean ativo = true;
	public boolean jaAdd = false;
	private boolean chip;
	private Color fontColor;
	private BufferedImage imageParaPintar;
	private Graphics2D g2;
	
	public PontosGanhosAnima�ao(String valor, int x, int y, boolean isChips) {
		
		this.valor = "+" + valor;
		
		//acertar posi�ao de texto quando ma�a estiver no lado extremo direito da tela
		if (x >= 340) {
			this.xPosi�ao = x - 30;
		} else if (x >= 320) {
			this.xPosi�ao = x - 20;
		} else {
			this.xPosi�ao = x;
		}
	
		this.yPosi�ao = y + 55;//para compensar GUI q mede 60 e o -5 � para q string apare�a um pouco em cima
		//acerta a cor
		if (isChips) {
			fontColor = new Color(255, 215, 0);
			
		} else if (Principal.gameModeDrogas) {
			fontColor = new Color(255, 0, 255);
		} else {
			fontColor = Color.BLACK;
		}
		
	
		this.chip = isChips;
		
		setBounds(xPosi�ao, yPosi�ao, 50, 20);
		setOpaque(false);
		imageParaPintar = new BufferedImage(50, 20, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imageParaPintar.getGraphics();
		
		new Thread(this).start();
		System.out.println("crio pontos anima�ao");
	}
	
	public void atualizar() {
		tempo--;
		if (tempo == 0) {
			ativo = false;
			return;
		}
		yPosi�ao--;	
		
		setLocation(xPosi�ao, yPosi�ao);
		alpha = alpha -0.02f;
		
		
		
		
		
		g2.setFont(new Font("Press Start 2P", Font.BOLD, 11));
		
		if (chip) {
			g2.setColor(Color.BLACK);
			g2.drawString(valor, 2, 20);
		}
		
		g2.setColor(fontColor);
		g2.drawString(valor, 0, 18);
		
		if (Principal.gameModeDrogas && !chip) {
			if (tempo % 5 == 0 || (tempo + 1) % 5 == 0) {
				g2.setColor(Color.BLACK);
				g2.drawString(valor, 0, 18);
			}
		}
		
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void paintComponent(Graphics g) {
		synchronized (Cell.class) {
			while(CampoPanel.deformando) {
				try {
					Cell.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2.drawImage(imageParaPintar, 0, 0, this);	
		}
	}

	@Override
	public void run() {
		while (ativo) {
			
			atualizar();
			//repaint();
			
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("pontos anima�ao finalizado");
	}
}
