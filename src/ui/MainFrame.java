package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The main application frame that serves as the container for all views.
 * <p>
 * This class provides the structural foundation for the application's user
 * interface. It maintains a single content panel where different sections
 * (e.g., login screen, home menu, game panels) are dynamically loaded and
 * replaced at runtime.
 * </p>
 *
 * @author Davitroon
 * @since 3.3
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 478);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}

	/**
	 * Replaces the current panel with a new one.
	 * <p>
	 * Called by ViewController when switching between panels.
	 * </p>
	 * 
	 * @param newPanel The new panel to display.
	 * @since 3.3
	 */
	public void setView(JPanel newPanel) {
		contentPane.removeAll();
		contentPane.add(newPanel, BorderLayout.CENTER);
		contentPane.revalidate();
		contentPane.repaint();
	}
}