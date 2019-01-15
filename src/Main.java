import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")

public class Main extends javax.swing.JFrame implements ActionListener, ChangeListener {
	private javax.swing.JLabel jLabel1;
	static javax.swing.JTextField jTextField1;
	private JTextField textField;
	private JLabel lblThumbnails;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel lblNombreNuevo;
	private JLabel lblNombreAntiguo;
	private JButton btnActualizar;
	private JButton btnActualizar_1;
	private JLabel label;
	private JTextField textField_4;
	private JButton btnEliminar;

	public static Connection conectarbd() {
		try {

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException ex) {
				System.out.println("Error al registrar el driver de MySQL: " + ex);
			}
			Connection connection = null;

			connection = DriverManager.getConnection("jdbc:mysql://localhost/usuarios", "root", "rootroot");

			return connection;
		} catch (java.sql.SQLException sqle) {
			return null;

		}

	}

	protected static void insertarCoche(String nombre, int precio, String fecha) throws ParseException, SQLException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date date = dateFormat.parse(fecha);
		long time = date.getTime();
		insertarRegistro(conectarbd(), nombre, precio, new Timestamp(time));
	}

	public static void insertarRegistro(Connection conn, String nombre, int precio, Timestamp timestamp)
			throws SQLException {
		Statement statement = conn.createStatement();

		statement.executeUpdate("INSERT INTO COCHE (Nombre,Precio,Fecha) VALUES('" + nombre + "'," + precio + "," + "'"
				+ timestamp + "')");

	}

	public static void actualizarRegistro(Connection conn, String nombre_antiguo, String nombre_nuevo)
			throws SQLException {
		Statement statement = conn.createStatement();

		statement.executeUpdate("UPDATE coche SET Nombre='" + nombre_nuevo + "' WHERE nombre='" + nombre_antiguo + "'");
	}

	public static void eliminarRegistro(Connection conn, String nombre) throws SQLException {
		Statement statement = conn.createStatement();

		statement.executeUpdate("DELETE FROM coche WHERE Nombre='" + nombre + "'");

	}

	public static void verRegistro(Connection conn) throws SQLException {
		System.out.println("Nombre - Precio - Fecha\n");
		System.out.println("////////////////////////////////\n");
		String sql = "SELECT * FROM coche";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			System.out.println(result.getString(2) + " - " + result.getString(3) + " - " + result.getString(4));
		}
		System.out.println("\n////////////////////////////////\n");
	}

	public Main() throws SQLException {
		setAlwaysOnTop(true);
		setTitle("Coches");
		setType(Type.UTILITY);
		initComponents();
		this.setVisible(true);
		verRegistro(conectarbd());
	}

	private void initComponents() {

		jTextField1 = new javax.swing.JTextField();
		jTextField1.setHorizontalAlignment(SwingConstants.LEFT);
		jTextField1.setToolTipText("");

		jLabel1 = new javax.swing.JLabel();
		jLabel1.setText("Nombre");

		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);

		jTextField1.setFont(new Font("Tahoma", Font.BOLD, 24));

		jLabel1.setFont(new Font("Tahoma", Font.BOLD, 20));

		JLabel lblNewLabel = new JLabel("");

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setToolTipText("");
		textField.setFont(new Font("Tahoma", Font.BOLD, 24));

		lblThumbnails = new JLabel("Precio");

		lblThumbnails.setFont(new Font("Tahoma", Font.BOLD, 20));

		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					String nombre = jTextField1.getText();
					int precio = Integer.parseInt(textField.getText());
					String fecha = textField_1.getText();
					insertarCoche(nombre, precio, fecha);
					Coche coche = new Coche();
					coche.setNombre(nombre);
					jTextField1.setText(coche.getNombre());
				} catch (ParseException | SQLException e) {

					e.printStackTrace();
				}
			}
		});

		textField_1 = new JTextField();
		textField_1.setToolTipText("");
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setFont(new Font("Tahoma", Font.BOLD, 24));

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 20));

		textField_2 = new JTextField();
		textField_2.setToolTipText("");
		textField_2.setHorizontalAlignment(SwingConstants.LEFT);
		textField_2.setFont(new Font("Tahoma", Font.BOLD, 24));

		textField_3 = new JTextField();
		textField_3.setToolTipText("");
		textField_3.setHorizontalAlignment(SwingConstants.LEFT);
		textField_3.setFont(new Font("Tahoma", Font.BOLD, 24));

		lblNombreNuevo = new JLabel("Nombre nuevo");
		lblNombreNuevo.setFont(new Font("Tahoma", Font.BOLD, 20));

		lblNombreAntiguo = new JLabel();
		lblNombreAntiguo.setText("Nombre antiguo");
		lblNombreAntiguo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreAntiguo.setFont(new Font("Tahoma", Font.BOLD, 20));

		btnActualizar = new JButton("Actualizar");

		btnActualizar_1 = new JButton("Actualizar");
		btnActualizar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					actualizarRegistro(conectarbd(), textField_2.getText(), textField_3.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		label = new JLabel();
		label.setText("Nombre antiguo");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 20));

		textField_4 = new JTextField();
		textField_4.setToolTipText("");
		textField_4.setHorizontalAlignment(SwingConstants.LEFT);
		textField_4.setFont(new Font("Tahoma", Font.BOLD, 24));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					eliminarRegistro(conectarbd(), textField_4.getText());
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap(751, Short.MAX_VALUE).addComponent(lblNewLabel)
						.addGap(81))
				.addGroup(layout.createSequentialGroup().addGap(28).addGroup(layout
						.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addGap(359)
								.addComponent(btnEliminar, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(jLabel1).addComponent(jTextField1))
										.addGap(18)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(textField, GroupLayout.PREFERRED_SIZE, 105,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblThumbnails)))
								.addComponent(lblNombreAntiguo)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblFecha, GroupLayout.PREFERRED_SIZE, 63,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(layout.createSequentialGroup()
												.addComponent(lblNombreNuevo, GroupLayout.PREFERRED_SIZE, 170,
														GroupLayout.PREFERRED_SIZE)
												.addGap(330).addComponent(btnActualizar, GroupLayout.PREFERRED_SIZE, 83,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 178,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(layout.createSequentialGroup().addGap(10)
																.addComponent(textField_3, GroupLayout.PREFERRED_SIZE,
																		105, GroupLayout.PREFERRED_SIZE)))
												.addGap(40)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(btnActualizar_1, GroupLayout.PREFERRED_SIZE, 83,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnInsertar, GroupLayout.PREFERRED_SIZE, 83,
																GroupLayout.PREFERRED_SIZE))
												.addGap(282)))))));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel1)
								.addComponent(lblThumbnails))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createSequentialGroup()
								.addComponent(lblFecha, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnInsertar, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
										.addComponent(textField_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))))
				.addGap(31)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNombreAntiguo, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNombreNuevo, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(btnActualizar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnActualizar_1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addGap(18).addComponent(label, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
				.addGap(28)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEliminar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGap(187).addComponent(lblNewLabel).addContainerGap()));
		getContentPane().setLayout(layout);
		setSize(new Dimension(613, 360));
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) throws SQLException {
		new Main();
	}

	public void actionPerformed(ActionEvent arg0) {

	}

	public void stateChanged(ChangeEvent e) {
	}
}