package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Ensures that the required database and tables exist before connecting.
 * <p>
 * SQLite version (no MySQL dependencies)
 * </p>
 *
 * @author
 * @since 3.3
 */
public class DatabaseInitializer {

	private Connection connection;

	public DatabaseInitializer(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Ensures that the database and its structure exist. If not, it creates the
	 * whole database.
	 */
    public void ensureDatabaseExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {

			// ---- TABLES ----
			stmt.execute("""
					    CREATE TABLE IF NOT EXISTS users (
					        id INTEGER PRIMARY KEY AUTOINCREMENT,
					        username TEXT NOT NULL UNIQUE,
					        password TEXT NOT NULL,
					        email TEXT NOT NULL,
					        last_access DATETIME DEFAULT CURRENT_TIMESTAMP,
					        verified_email INTEGER NOT NULL DEFAULT 1,
					        remember_login INTEGER NOT NULL DEFAULT 0
					    );
					""");

			stmt.execute("""
					    CREATE TABLE IF NOT EXISTS clients (
					        id INTEGER PRIMARY KEY AUTOINCREMENT,
					        client_name TEXT NOT NULL,
					        age INTEGER NOT NULL,
					        gender TEXT CHECK(gender IN ('M','F','O')) NOT NULL,
					        active_status INTEGER NOT NULL DEFAULT 1,
					        balance REAL NOT NULL,
					        user_profile INTEGER NOT NULL,
					        FOREIGN KEY (user_profile) REFERENCES users(id) ON DELETE CASCADE
					    );
					""");

			stmt.execute("""
					    CREATE TABLE IF NOT EXISTS games (
					        id INTEGER PRIMARY KEY AUTOINCREMENT,
					        game_type TEXT CHECK(game_type IN ('Blackjack','SlotMachine')) NOT NULL,
					        active_status INTEGER NOT NULL DEFAULT 1,
					        money_pool REAL NOT NULL,
					        user_profile INTEGER NOT NULL,
					        FOREIGN KEY (user_profile) REFERENCES users(id) ON DELETE CASCADE
					    );
					""");

			stmt.execute("""
					    CREATE TABLE IF NOT EXISTS game_sessions (
					        id INTEGER PRIMARY KEY AUTOINCREMENT,
					        client_id INTEGER,
					        game_id INTEGER,
					        game_type TEXT CHECK(game_type IN ('Blackjack','SlotMachine')) NOT NULL,
					        bet_result REAL NOT NULL,
					        session_date DATETIME DEFAULT CURRENT_TIMESTAMP,
					        user_profile INTEGER NOT NULL,
					        FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE SET NULL,
					        FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE SET NULL,
					        FOREIGN KEY (user_profile) REFERENCES users(id) ON DELETE CASCADE
					    );
					""");

			stmt.execute("""
					    CREATE TABLE IF NOT EXISTS domains (
					        domain_type TEXT,
					        tld TEXT,
					        manager TEXT
					    );
					""");

			// ---- TRIGGERS ----
			stmt.execute("DROP TRIGGER IF EXISTS verify_email_insert;");
			stmt.execute("DROP TRIGGER IF EXISTS verify_email_update;");

			stmt.execute("""
					    CREATE TRIGGER verify_email_insert
					    BEFORE INSERT ON users
					    FOR EACH ROW
					    BEGIN
					        SELECT
					            CASE
					                WHEN INSTR(NEW.email, '@') = 0
					                     OR INSTR(NEW.email, '.') = 0
					                THEN RAISE(IGNORE)
					            END;
					    END;
					""");

			stmt.execute("""
					    CREATE TRIGGER verify_email_update
					    BEFORE UPDATE OF email ON users
					    FOR EACH ROW
					    BEGIN
					        SELECT
					            CASE
					                WHEN INSTR(NEW.email, '@') = 0
					                     OR INSTR(NEW.email, '.') = 0
					                THEN RAISE(IGNORE)
					            END;
					    END;
					""");

			// Insertar datos por defecto
			String insertDefaults = """
					INSERT INTO domains (domain_type, tld, manager) VALUES  ('generic', '.aaa', 'American Automobile Association, Inc.'),

					('generic', '.aarp', NULL),

					('generic', '.abarth', NULL),

					('generic', '.abb', 'ABB Ltd'),

					('generic', '.abbott', 'Abbott Laboratories, Inc.'),

					('generic', '.abbvie', 'AbbVie Inc.'),

					('generic', '.abc', 'Disney Enterprises, Inc.'),

					('generic', '.able', NULL),

					('generic', '.abogado', 'Registry Services, LLC'),

					('generic', '.abudhabi', 'Abu Dhabi Systems and Information Centre'),

					('country-code', '.ac', 'Internet Computer Bureau Limited'),

					('generic', '.academy', 'Binky Moon, LLC'),

					('generic', '.accenture', 'Accenture plc'),

					('generic', '.accountant', 'dot Accountant Limited'),

					('generic', '.accountants', 'Binky Moon, LLC'),

					('generic', '.aco', 'ACO Severin Ahlmann GmbH & Co. KG'),

					('generic', '.active', NULL),

					('generic', '.actor', 'Dog Beach, LLC'),

					('country-code', '.ad', 'Andorra Telecom'),

					('generic', '.adac', NULL),

					('generic', '.ads', 'Charleston Road Registry Inc.'),

					('generic', '.adult', 'ICM Registry AD LLC'),

					('country-code', '.ae', 'Telecommunications and Digital Government Regulatory Authority'),

					 ('country-code', '.am', 'ISOC AM'),

					 ('country-code', '.americanexpress', 'American Express Travel Related Services Company, Inc.'),

					 ('generic', '.americanfamily', 'AmFam, Inc.'),

					 ('generic', '.amex', 'American Express Travel Related Services Company, Inc.'),

					 ('generic', '.amfam', 'AmFam, Inc.'),

					 ('generic', '.amica', 'Amica Mutual Insurance Company'),

					 ('generic', '.amsterdam', 'Gemeente Amsterdam'),

					 ('country-code', '.an', 'Netherlands Antilles (Retired TLD)'),

					 ('generic', '.analytics', 'Campus IP LLC'),

					 ('generic', '.android', 'Charleston Road Registry Inc.'),

					 ('generic', '.anquan', 'QIHOO 360 TECHNOLOGY CO. LTD.'),

					 ('country-code', '.ao', 'Faculdade de Engenharia da Universidade Agostinho Neto'),

					 ('generic', '.aol', 'Oath Inc.'),

					 ('generic', '.apartments', 'Binky Moon, LLC'),

					 ('generic', '.app', 'Charleston Road Registry Inc.'),

					 ('generic', '.apple', 'Apple Inc.'),

					 ('generic', '.aquarelle', 'Aquarelle.com'),

					 ('generic', '.arab', 'League of Arab States'),

					 ('generic', '.aramco', 'Aramco Services Company'),

					 ('generic', '.archi', 'STARTING DOT LIMITED'),

					 ('generic', '.army', 'United TLD Holdco Ltd.'),

					 ('generic', '.arpa', 'Internet Assigned Numbers Authority'),

					 ('generic', '.art', 'UK Creative Ideas Limited'),

					 ('generic', '.arte', 'Association Relative à la Télévision Européenne G.E.I.E.'),

					 ('generic', '.asda', 'Wal-Mart Stores, Inc.'),

					 ('country-code', '.asia', 'DotAsia Organisation Ltd.'),

					 ('generic', '.associates', 'Binky Moon, LLC'),

					 ('generic', '.athleta', 'The Gap, Inc.'),

					 ('country-code', '.attorney', 'Dog Beach, LLC'),

					 ('generic', '.au', 'au Domain Administration (auDA)'),

					 ('generic', '.auction', 'United TLD Holdco Ltd.'),

					 ('generic', '.audi', 'AUDI Aktiengesellschaft'),

					 ('generic', '.audible', 'Amazon Registry Services, Inc.'),

					 ('generic', '.audio', 'Uniregistry, Corp.'),

					 ('generic', '.auspost', 'Australian Postal Corporation'),

					 ('generic', '.author', 'Amazon Registry Services, Inc.'),

					 ('generic', '.auto', 'Cars Registry Limited'),

					 ('generic', '.autos', 'DERAutos, LLC'),

					 ('generic', '.avianca', 'Aerovias del Continente Americano S.A. Avianca'),

					 ('generic', '.aw', 'SETAR'),

					 ('generic', '.aws', 'Amazon Registry Services, Inc.'),

					 ('generic', '.ax', 'Ålands landskapsregering'),

					 ('generic', '.axa', 'AXA Group Operations SAS'),

					 ('generic', '.az', 'IntraNS'),

					 ('generic', '.azure', 'Microsoft Corporation'),

					('generic', '.baby', 'Johnson & Johnson Services, Inc.'),

					('generic', '.baidu', 'Baidu, Inc.'),

					('generic', '.banamex', 'Citigroup Inc.'),

					('generic', '.bananarepublic', 'The Gap, Inc.'),

					 ('country-code', '.band', 'Binky Moon, LLC'),

					 ('generic', '.bank', 'fTLD Registry Services, LLC'),

					 ('generic', '.bar', 'Punto 2012 Sociedad Anonima Promotora de Inversion de Capital Variable'),

					 ('generic', '.barcelona', 'Municipi de Barcelona'),

					 ('generic', '.barclaycard', 'Barclays Bank PLC'),

					 ('generic', '.barclays', 'Barclays Bank PLC'),

					 ('generic', '.barefoot', 'Gallo Vineyards, Inc.'),

					 ('generic', '.bargains', 'Binky Moon, LLC'),

					 ('generic', '.baseball', 'MLB Advanced Media DH, LLC'),

					 ('generic', '.basketball', 'Fédération Internationale de Basketball (FIBA)'),

					 ('generic', '.bauhaus', 'Werkhaus GmbH'),

					 ('generic', '.bayern', 'Bayern Connect GmbH'),

					 ('generic', '.bbc', 'British Broadcasting Corporation'),

					 ('generic', '.bbt', 'BB&T Corporation'),

					 ('generic', '.bbva', 'BANCO BILBAO VIZCAYA ARGENTARIA, S.A.'),

					 ('generic', '.bcg', 'The Boston Consulting Group, Inc.'),

					 ('generic', '.bcn', 'Municipi de Barcelona'),

					 ('generic', '.beats', 'Beats Electronics, LLC'),

					 ('generic', '.beauty', 'LOréal'),

					 ('generic', '.beer', 'Minds + Machines Group Limited'),

					 ('generic', '.bentley', 'Bentley Motors Limited'),

					 ('generic', '.berlin', 'dotBERLIN GmbH & Co. KG'),

					 ('generic', '.best', 'BestTLD Pty Ltd'),

					 ('generic', '.bestbuy', 'BBY Solutions, Inc.'),

					 ('generic', '.bet', 'Afilias plc'),

					 ('generic', '.bharti', 'Bharti Enterprises (Holding) Private Limited'),

					 ('generic', '.bible', 'American Bible Society'),

					 ('generic', '.bid', 'dot Bid Limited'),

					 ('generic', '.bike', 'Binky Moon, LLC'),

					 ('generic', '.bing', 'Microsoft Corporation'),

					 ('generic', '.bingo', 'Binky Moon, LLC'),

					 ('generic', '.bio', 'STARTING DOT LIMITED'),

					 ('generic', '.black', 'Afilias plc'),

					 ('generic', '.blackfriday', 'UNR Corp.'),

					 ('generic', '.blanco', 'Blanco GmbH + Co KG'),

					 ('generic', '.blockbuster', 'Dish DBS Corporation'),

					 ('generic', '.blog', 'Automattic, Inc.'),

					 ('generic', '.bloomberg', 'Bloomberg IP Holdings LLC'),

					 ('generic', '.blue', 'Afilias plc'),

					 ('generic', '.bms', 'Bristol-Myers Squibb Company'),

					 ('generic', '.bmw', 'Bayerische Motoren Werke Aktiengesellschaft'),

					 ('generic', '.bnl', 'Banca Nazionale del Lavoro'),

					 ('generic', '.bnpparibas', 'BNP Paribas'),

					 ('generic', '.boats', 'DERBoats, LLC'),

					 ('generic', '.boehringer', 'Boehringer Ingelheim International GmbH'),

					 ('generic', '.bofa', 'NMS Services, Inc.'),

					 ('generic', '.bom', 'Núcleo de Informação e Coordenação do Ponto BR - NIC.br'),

					 ('generic', '.bond', 'Bond University Limited'),

					 ('generic', '.boo', 'Charleston Road Registry Inc.'),

					 ('generic', '.book', 'Amazon Registry Services, Inc.'),

					 ('generic', '.booking', 'Booking.com B.V.'),

					 ('generic', '.bosch', 'Robert Bosch GMBH'),

					 ('generic', '.bostik', 'Bostik SA'),

					 ('generic', '.boston', 'Boston TLD Management, LLC'),

					 ('generic', '.bot', 'Amazon Registry Services, Inc.'),

					 ('generic', '.boutique', 'Binky Moon, LLC'),

					 ('generic', '.box', 'Intercap Registry Inc.'),

					 ('generic', '.br', 'Comite Gestor da Internet no Brasil'),

					 ('generic', '.bradesco', 'Banco Bradesco S.A.'),

					 ('generic', '.bridgestone', 'Bridgestone Corporation'),

					 ('generic', '.broadway', 'Celebrate Broadway, Inc.'),

					 ('generic', '.broker', 'Dog Beach, LLC'),

					 ('generic', '.brother', 'Brother Industries, Ltd.'),

					 ('generic', '.brussels', 'DNS.be vzw'),

					 ('generic', '.budapest', 'Minds + Machines Group Limited'),

					 ('generic', '.bugatti', 'Bugatti International SA'),

					 ('generic', '.build', 'Plan Bee LLC'),

					 ('generic', '.builders', 'Binky Moon, LLC'),

					 ('generic', '.business', 'Binky Moon, LLC'),

					 ('generic', '.buy', 'Amazon Registry Services, Inc.'),

					 ('generic', '.buzz', 'DOTSTRATEGY CO.'),

					 ('generic', '.bzh', 'Association www.bzh'),

					 ('generic', '.ca', 'Canadian Internet Registration Authority (CIRA) Autorité canadienne pour les enregistrements Internet (ACEI)'),

					 ('generic', '.cab', 'Binky Moon, LLC'),

					 ('generic', '.cafe', 'Binky Moon, LLC'),

					 ('generic', '.cal', 'Charleston Road Registry Inc.'),

					 ('generic', '.call', 'Amazon Registry Services, Inc.'),

					 ('generic', '.calvinklein', 'PVH gTLD Holdings LLC'),

					 ('generic', '.cam', 'AC Webconnecting Holding B.V.'),

					 ('generic', '.camera', 'Binky Moon, LLC'),

					 ('generic', '.camp', 'Binky Moon, LLC'),

					 ('generic', '.cancerresearch', 'Australian Cancer Research Foundation'),

					 ('generic', '.canon', 'Canon Inc.'),

					 ('generic', '.capetown', 'ZA Central Registry NPC trading as ZA Central Registry'),

					 ('generic', '.capital', 'Binky Moon, LLC'),

					 ('generic', '.capitalone', 'Capital One Financial Corporation'),

					 ('generic', '.car', 'Cars Registry Limited'),

					 ('generic', '.caravan', 'Caravan Club Limited'),

					 ('generic', '.cards', 'Binky Moon, LLC'),

					 ('generic', '.care', 'Binky Moon, LLC'),

					 ('generic', '.career', 'dotCareer LLC'),

					 ('generic', '.careers', 'Binky Moon, LLC'),

					 ('generic', '.cars', 'Cars Registry Limited'),

					 ('generic', '.casa', 'Dog Beach, LLC'),

					 ('generic', '.case', 'CASE Inc.'),

					 ('generic', '.caseih', 'CNH Industrial N.V.'),

					 ('generic', '.cash', 'Binky Moon, LLC'),

					 ('generic', '.casino', 'Binky Moon, LLC'),

					 ('generic', '.cat', 'Fundacio puntCAT'),

					 ('generic', '.catering', 'Binky Moon, LLC'),

					 ('generic', '.catholic', 'Pontificium Consilium de Comunicationibus Socialibus (PCCS) / Pontifical Council for Social Communication'),

					 ('generic', '.cba', 'COMMONWEALTH BANK OF AUSTRALIA'),

					 ('generic', '.cbn', 'The Christian Broadcasting Network, Inc.'),

					 ('generic', '.cbre', 'CBRE, Inc.'),

					 ('generic', '.cbs', 'CBS Domains Inc.'),

					 ('generic', '.center', 'Binky Moon, LLC'),

					 ('generic', '.ceo', 'CEOTLD Pty Ltd'),

					 ('generic', '.cern', 'European Organization for Nuclear Research ("CERN")'),

					 ('generic', '.cfa', 'CFA Institute'),

					 ('generic', '.cfd', 'DOTCFD REGISTRY LTD'),

					 ('generic', '.ch', 'SWITCH The Swiss Education & Research Network'),

					 ('generic', '.chanel', 'Chanel International B.V.'),

					 ('generic', '.channel', 'Charleston Road Registry Inc.'),

					 ('generic', '.charity', 'Corn Lake, LLC'),

					 ('generic', '.chase', 'JPMorgan Chase & Co.'),

					 ('generic', '.chat', 'Binky Moon, LLC'),

					 ('generic', '.cheap', 'Binky Moon, LLC'),

					 ('generic', '.chintai', 'CHINTAI Corporation'),

					 ('generic', '.christmas', 'Uniregistry, Corp.'),

					 ('generic', '.chrome', 'Charleston Road Registry Inc.'),

					 ('generic', '.church', 'United TLD Holdco Ltd.'),

					 ('generic', '.cipriani', 'Hotel Cipriani Srl'),

					 ('generic', '.circle', 'Amazon Registry Services, Inc.'),

					 ('generic', '.cisco', 'Cisco Technology, Inc.'),

					 ('generic', '.citadel', 'Citadel Domain LLC'),

					 ('generic', '.citi', 'Citigroup Inc.'),

					 ('generic', '.citic', 'CITIC Group Corporation'),

					 ('generic', '.city', 'Binky Moon, LLC'),

					 ('generic', '.cityeats', 'Lifestyle Domain Holdings, Inc.'),

					 ('generic', '.ck', 'Telecom Cook Islands Ltd.'),

					 ('generic', '.claims', 'Binky Moon, LLC'),

					 ('generic', '.cleaning', 'Binky Moon, LLC'),

					 ('generic', '.click', 'Uniregistry, Corp.'),

					 ('generic', '.clinic', 'Binky Moon, LLC'),

					 ('generic', '.clinique', 'The Estée Lauder Companies Inc.'),

					 ('generic', '.clothing', 'Binky Moon, LLC'),

					 ('generic', '.cloud', 'ARUBA PEC S.p.A.'),

					 ('generic', '.club', '.CLUB DOMAINS, LLC'),

					 ('generic', '.clubmed', 'Club Méditerranée S.A.'),

					 ('generic', '.cm', 'Cameroon Telecommunications (CAMTEL)'),

					 ('generic', '.cn', 'China Internet Network Information Center (CNNIC)'),

					 ('generic', '.co', '.CO Internet S.A.S.'),

					 ('generic', '.coach', 'Koko Island, LLC'),

					 ('generic', '.codes', 'Binky Moon, LLC'),

					 ('generic', '.coffee', 'Binky Moon, LLC'),

					 ('generic', '.college', 'XYZ.COM LLC'),

					 ('generic', '.cologne', 'NetCologne Gesellschaft für Telekommunikation mbH'),

					 ('generic', '.com', 'VeriSign Global Registry Services'),

					 ('generic', '.comcast', 'Comcast IP Holdings I, LLC'),

					 ('generic', '.commbank', 'COMMONWEALTH BANK OF AUSTRALIA'),

					 ('generic', '.community', 'Binky Moon, LLC'),

					 ('generic', '.company', 'Binky Moon, LLC'),

					 ('generic', '.compare', 'iSelect Ltd'),

					 ('generic', '.computer', 'Binky Moon, LLC'),

					 ('generic', '.comsec', 'VeriSign, Inc.'),

					 ('generic', '.condos', 'Binky Moon, LLC'),

					 ('generic', '.construction', 'Binky Moon, LLC'),

					 ('generic', '.consulting', 'United TLD Holdco, LTD.'),

					 ('generic', '.contact', 'Top Level Spectrum, Inc.'),

					 ('generic', '.contractors', 'Binky Moon, LLC'),

					 ('generic', '.cooking', 'Top Level Domain Holdings Limited'),

					 ('generic', '.cookingchannel', 'Lifestyle Domain Holdings, Inc.'),

					 ('generic', '.cool', 'Binky Moon, LLC'),

					 ('generic', '.coop', 'DotCooperation LLC'),

					 ('generic', '.corsica', 'Collectivité de Corse - Direction du Système Information'),

					 ('generic', '.country', 'Top Level Domain Holdings Limited'),

					 ('generic', '.coupon', 'Amazon Registry Services, Inc.'),

					 ('generic', '.coupons', 'Binky Moon, LLC'),

					 ('generic', '.courses', 'Open Universities Australia Pty Ltd'),

					 ('generic', '.credit', 'Binky Moon, LLC'),

					 ('generic', '.creditcard', 'Binky Moon, LLC'),

					 ('generic', '.creditunion', 'CUNA Performance Resources, LLC'),

					 ('generic', '.cricket', 'dot Cricket Limited'),

					 ('generic', '.crown', 'Crown Equipment Corporation'),

					 ('generic', '.crs', 'Federated Co-operatives Limited'),

					 ('generic', '.cruise', 'Binky Moon, LLC'),

					 ('generic', '.cruises', 'Binky Moon, LLC'),

					 ('generic', '.csc', 'Alliance-One Services, Inc.'),

					 ('generic', '.cuisinella', 'SALM S.A.S.'),

					 ('generic', '.cx', 'Christmas Island Domain Administration Limited'),

					 ('generic', '.cymru', 'Nominet UK'),

					 ('generic', '.cyou', 'Shortdot SA'),

					 ('generic', '.cz', 'CZ.NIC, z.s.p.o.'),

					 ('generic', '.dabur', 'Dabur India Limited'),

					 ('generic', '.dad', 'Charleston Road Registry Inc.'),

					 ('generic', '.dance', 'United TLD Holdco Ltd.'),

					 ('generic', '.data', 'Dish DBS Corporation'),

					 ('generic', '.date', 'dot Date Limited'),

					 ('generic', '.dating', 'Dog Beach, LLC'),

					 ('generic', '.datsun', 'NISSAN MOTOR CO., LTD.'),

					 ('generic', '.day', 'Charleston Road Registry Inc.'),

					 ('generic', '.dclk', 'Charleston Road Registry Inc.'),

					 ('generic', '.dds', 'Dog Beach, LLC'),

					 ('generic', '.deal', 'Amazon Registry Services, Inc.'),

					 ('generic', '.dealer', 'Dealer Dot Com, Inc.'),

					 ('generic', '.deals', 'Binky Moon, LLC'),

					 ('generic', '.degree', 'United TLD Holdco, Ltd'),

					 ('generic', '.delivery', 'Binky Moon, LLC'),

					 ('generic', '.dell', 'Dell Inc.'),

					 ('generic', '.deloitte', 'Deloitte Touche Tohmatsu'),

					 ('generic', '.delta', 'Delta Air Lines, Inc.'),

					 ('generic', '.democrat', 'United TLD Holdco, Ltd'),

					 ('generic', '.dental', 'United TLD Holdco, Ltd'),

					 ('generic', '.dentist', 'United TLD Holdco, Ltd'),

					 ('generic', '.desi', 'Desi Networks LLC'),

					 ('generic', '.design', 'Top Level Design, LLC'),

					 ('generic', '.dev', 'Charleston Road Registry Inc.'),

					 ('generic', '.dhl', 'Deutsche Post AG'),

					 ('generic', '.diamonds', 'Binky Moon, LLC'),

					 ('generic', '.diet', 'Uniregistry, Corp.'),

					 ('generic', '.digital', 'Binky Moon, LLC'),

					 ('generic', '.direct', 'Binky Moon, LLC'),

					 ('generic', '.directory', 'Binky Moon, LLC'),

					 ('generic', '.discount', 'Binky Moon, LLC'),

					 ('generic', '.discover', 'Discover Financial Services'),

					 ('generic', '.dish', 'Dish DBS Corporation'),

					 ('generic', '.diy', 'Lifestyle Domain Holdings, Inc.'),

					 ('generic', '.dj', 'Djibouti Telecom SA'),

					 ('generic', '.dk', 'Dansk Internet Forum'),

					 ('generic', '.dm', 'DotDM Corporation'),

					 ('generic', '.dnp', 'Dai Nippon Printing Co., Ltd.'),

					 ('generic', '.do', 'Pontificia Universidad Católica Madre y MaestraRecinto Santo Tomás de Aquino'),

					 ('generic', '.docs', 'Charleston Road Registry Inc.'),

					 ('generic', '.doctor', 'Brice Trail, LLC'),

					 ('generic', '.dodge', 'FCA US LLC.'),

					 ('generic', '.dog', 'Binky Moon, LLC'),

					 ('generic', '.doha', 'Communications Regulatory Authority'),

					 ('generic', '.domains', 'Binky Moon, LLC'),

					 ('generic', '.doosan', 'Doosan Corporation'),

					 ('generic', '.dot', 'Dish DBS Corporation'),

					 ('generic', '.download', 'dot Support Limited'),

					 ('generic', '.drive', 'Charleston Road Registry Inc.'),

					 ('generic', '.dtv', 'Dish DBS Corporation'),

					 ('generic', '.dubai', 'Dubai Multi Commodities Centre Authority (DMCC)'),

					 ('generic', '.duck', 'Johnson Shareholdings, Inc.'),

					 ('generic', '.dunlop', 'The Goodyear Tire & Rubber Company'),

					 ('generic', '.duns', 'The Dun & Bradstreet Corporation'),

					 ('generic', '.dupont', 'E. I. du Pont de Nemours and Company'),

					 ('generic', '.durban', 'ZA Central Registry NPC trading as ZA Central Registry'),

					 ('generic', '.dvag', 'Deutsche Vermögensberatung Aktiengesellschaft DVAG'),

					 ('generic', '.dvr', 'Dish DBS Corporation'),

					 ('generic', '.dz', 'CERIST'),

					 ('generic', '.earth', 'Interlink Co., Ltd.'),

					 ('generic', '.eat', 'Charleston Road Registry Inc.'),

					 ('generic', '.eco', 'Big Room Inc.'),

					 ('generic', '.edeka', 'EDEKA Verband kaufmännischer Genossenschaften e.V.'),

					 ('generic', '.edu', 'Educause'),

					 ('generic', '.education', 'Binky Moon, LLC'),

					 ('generic', '.email', 'Binky Moon, LLC'),

					 ('generic', '.emerck', 'Merck KGaA'),

					 ('generic', '.energy', 'Binky Moon, LLC'),

					 ('generic', '.engineer', 'Binky Moon, LLC'),

					 ('generic', '.engineering', 'Binky Moon, LLC'),

					 ('generic', '.enterprises', 'Binky Moon, LLC'),

					 ('generic', '.epson', 'Seiko Epson Corporation'),

					 ('generic', '.equipment', 'Binky Moon, LLC'),

					 ('generic', '.ericsson', 'Telefonaktiebolaget L M Ericsson'),

					 ('generic', '.erni', 'Ernst & Young GmbH Wirtschaftsprüfungsgesellschaft'),

					 ('generic', '.es', 'Red.es'),

					 ('generic', '.esq', 'Charleston Road Registry Inc.'),

					 ('generic', '.estate', 'Binky Moon, LLC'),

					 ('generic', '.esurance', 'Esurance Insurance Company'),

					 ('generic', '.etisalat', 'Emirates Telecommunications Corporation ("Etisalat")'),

					 ('generic', '.eu', 'EURid vzw/asbl'),

					 ('generic', '.eurovision', 'European Broadcasting Union (EBU)'),

					 ('generic', '.eus', 'PuntuEUS Fundazioa'),

					 ('generic', '.events', 'Binky Moon, LLC'),

					 ('generic', '.exchange', 'Binky Moon, LLC'),

					 ('generic', '.expert', 'Binky Moon, LLC'),

					 ('generic', '.exposed', 'Binky Moon, LLC'),

					 ('generic', '.express', 'Binky Moon, LLC'),

					 ('generic', '.extraspace', 'Extra Space Storage LLC'),

					 ('generic', '.fage', 'Fage International S.A.'),

					 ('generic', '.fail', 'Binky Moon, LLC'),

					 ('generic', '.fairwinds', 'FairWinds Partners, LLC'),

					 ('generic', '.faith', 'dot Faith Limited'),

					 ('generic', '.family', 'Dog Beach, LLC'),

					 ('generic', '.fan', 'Dog Beach, LLC'),

					 ('generic', '.fans', 'Asiamix Digital Limited'),

					 ('generic', '.farm', 'Binky Moon, LLC'),

					 ('generic', '.farmers', 'Farmers Insurance Exchange'),

					 ('generic', '.fashion', 'Dog Beach, LLC'),

					 ('generic', '.fast', 'Amazon Registry Services, Inc.'),

					 ('generic', '.fedex', 'Federal Express Corporation'),

					 ('generic', '.feedback', 'Top Level Spectrum, Inc.'),

					 ('generic', '.ferrari', 'Fiat Chrysler Automobiles N.V.'),

					 ('generic', '.ferrero', 'Ferrero Trading Lux S.A.'),

					 ('generic', '.fi', 'Finnish Transport and Communications Agency (Traficom)'),

					 ('generic', '.fiat', 'Fiat Chrysler Automobiles N.V.'),

					 ('generic', '.fidelity', 'Fidelity Brokerage Services LLC'),

					 ('generic', '.fido', 'Rogers Communications Canada Inc.'),

					 ('generic', '.film', 'Motion Picture Domain Registry Pty Ltd'),

					 ('generic', '.final', 'Núcleo de Informação e Coordenação do Ponto BR - NIC.br'),

					 ('generic', '.finance', 'Binky Moon, LLC'),

					 ('generic', '.financial', 'Binky Moon, LLC'),

					 ('generic', '.fire', 'Amazon Registry Services, Inc.'),

					 ('generic', '.firestone', 'Bridgestone Corporation'),

					 ('generic', '.firmdale', 'Firmdale Holdings Limited'),

					 ('generic', '.fish', 'Binky Moon, LLC'),

					 ('generic', '.fishing', 'Top Level Domain Holdings Limited'),

					 ('generic', '.fit', 'Minds + Machines Group Limited'),

					 ('generic', '.fitness', 'Binky Moon, LLC'),

					 ('generic', '.fj', 'University of the South Pacific'),

					 ('generic', '.fk', 'Falkland Islands Government'),

					 ('generic', '.flickr', 'Yahoo Assets LLC'),

					 ('generic', '.flights', 'Binky Moon, LLC'),

					 ('generic', '.flir', 'FLIR Systems, Inc.'),

					 ('generic', '.florist', 'Binky Moon, LLC'),

					 ('generic', '.flowers', 'Uniregistry, Corp.'),

					 ('generic', '.fly', 'Charleston Road Registry Inc.'),

					 ('generic', '.fm', 'FSM Telecommunications Corporation'),

					 ('generic', '.fo', 'Føroya Tele P/F'),

					 ('generic', '.foo', 'Charleston Road Registry Inc.'),

					 ('generic', '.food', 'Lifestyle Domain Holdings, Inc.'),

					 ('generic', '.foodnetwork', 'Lifestyle Domain Holdings, Inc.'),

					 ('generic', '.football', 'Binky Moon, LLC'),

					 ('generic', '.ford', 'Ford Motor Company'),

					 ('generic', '.forex', 'Dotforex Registry Limited'),

					 ('generic', '.forsale', 'Binky Moon, LLC'),

					 ('generic', '.forum', 'Fegistry, LLC'),

					 ('generic', '.foundation', 'Binky Moon, LLC'),

					 ('generic', '.fox', 'FOX Registry, LLC'),

					 ('generic', '.fr', 'Association Française pour le Nommage Internet en Coopération (A.F.N.I.C.)'),

					 ('generic', '.free', 'Amazon Registry Services, Inc.'),

					 ('generic', '.fresenius', 'Fresenius Immobilien-Verwaltungs-GmbH'),

					 ('generic', '.frl', 'FRLregistry B.V.'),

					 ('generic', '.frogans', 'OP3FT'),

					 ('generic', '.frontdoor', 'Lifestyle Domain Holdings, Inc.'),

					 ('generic', '.frontier', 'Frontier Communications Corporation'),

					 ('generic', '.ftr', 'Frontier Communications Corporation'),

					 ('generic', '.fujitsu', 'Fujitsu Limited'),

					 ('generic', '.fujixerox', 'Xerox DNHC LLC'),

					 ('generic', '.fun', 'DotSpace, Inc.'),

					 ('generic', '.fund', 'Binky Moon, LLC'),

					 ('generic', '.furniture', 'Binky Moon, LLC'),

					 ('generic', '.futbol', 'Binky Moon, LLC'),

					 ('generic', '.fyi', 'Binky Moon, LLC'),

					 ('generic', '.ga', 'Agence des Technologies de l’Information et de la Communication (AGETIC)'),

					 ('generic', '.gal', 'Asociación puntoGAL'),

					 ('generic', '.gallery', 'Binky Moon, LLC'),

					 ('generic', '.gallo', 'Gallo Vineyards, Inc.'),

					 ('generic', '.gallup', 'Gallup, Inc.'),

					 ('generic', '.game', 'Uniregistry, Corp.'),

					 ('generic', '.games', 'United TLD Holdco Ltd.'),

					 ('generic', '.gap', 'The Gap, Inc.'),

					 ('generic', '.garden', 'Binky Moon, LLC'),

					 ('generic', '.gay', 'Top Level Design, LLC'),

					 ('generic', '.gb', 'Reserved Domain - IANA'),

					 ('generic', '.gbiz', 'Charleston Road Registry Inc.'),

					 ('generic', '.gd', 'The National Telecommunications Regulatory Commission (NTRC)'),

					 ('generic', '.gdn', 'Joint Stock Company "Navigation-information systems"'),

					 ('generic', '.ge', 'Caucasus Online'),

					 ('generic', '.gea', 'Géant Association'),

					 ('generic', '.gent', 'Combell NV'),

					 ('generic', '.genting', 'Resorts World Inc. Pte. Ltd.'),

					 ('generic', '.george', 'Wal-Mart Stores, Inc.'),

					 ('generic', '.gf', 'Net Plus'),

					 ('generic', '.gg', 'Island Networks Ltd.'),

					 ('generic', '.ggee', 'GMO Internet, Inc.'),

					 ('generic', '.gh', 'Network Computer Systems Ltd.'),

					 ('generic', '.gi', 'Sapphire Networks'),

					 ('generic', '.gift', 'Uniregistry, Corp.'),

					 ('generic', '.gifts', 'Binky Moon, LLC'),

					 ('generic', '.gives', 'Dog Beach, LLC'),

					 ('generic', '.giving', 'Giving Limited'),

					 ('generic', '.gl', 'TELE Greenland A/S'),

					 ('generic', '.glass', 'Binky Moon, LLC'),

					 ('generic', '.gle', 'Charleston Road Registry Inc.'),

					 ('generic', '.global', 'Dot Global Domain Registry Limited'),

					 ('generic', '.globo', 'Globo Comunicação e Participações S.A'),

					 ('generic', '.gmail', 'Charleston Road Registry Inc.'),

					 ('generic', '.gmbh', 'Binky Moon, LLC'),

					 ('generic', '.gmo', 'GMO Internet, Inc.'),

					 ('generic', '.gmx', '1&1 Mail & Media GmbH'),

					 ('generic', '.gn', 'Centre National des Sciences Halieutiques de Boussoura'),

					 ('generic', '.godaddy', 'Go Daddy East, LLC'),

					 ('generic', '.gold', 'Binky Moon, LLC'),

					 ('generic', '.goldpoint', 'YODOBASHI CAMERA CO.,LTD.'),

					 ('generic', '.golf', 'Binky Moon, LLC'),

					 ('generic', '.goo', 'NTT Resonant Inc.'),

					 ('generic', '.goodyear', 'The Goodyear Tire & Rubber Company'),

					 ('generic', '.goog', 'Charleston Road Registry Inc.'),

					 ('generic', '.google', 'Charleston Road Registry Inc.'),

					 ('generic', '.gop', 'Republican State Leadership Committee, Inc.'),

					 ('generic', '.got', 'Amazon Registry Services, Inc.'),

					 ('generic', '.gov', 'Cybersecurity and Infrastructure Security Agency'),

					 ('generic', '.gp', 'Networking Technologies Group'),

					 ('generic', '.gq', 'GETESA'),

					 ('generic', '.gr', 'ICS-FORTH GR'),

					 ('generic', '.grainger', 'Grainger Registry Services, LLC'),

					 ('generic', '.graphics', 'Binky Moon, LLC'),

					 ('generic', '.gratis', 'Binky Moon, LLC'),

					 ('generic', '.green', 'Afilias Limited'),

					 ('generic', '.gripe', 'Binky Moon, LLC'),

					 ('generic', '.grocery', 'Wal-Mart Stores, Inc.'),

					 ('generic', '.group', 'Binky Moon, LLC'),

					 ('generic', '.gs', 'Government of South Georgia and South Sandwich Islands (GSGSSI)'),

					 ('generic', '.gt', 'Universidad del Valle de Guatemala'),

					 ('generic', '.gucci', 'Guccio Gucci S.p.a.'),

					 ('generic', '.guge', 'Charleston Road Registry Inc.'),

					 ('generic', '.guide', 'Binky Moon, LLC'),

					 ('generic', '.guitars', 'Uniregistry, Corp.'),

					 ('generic', '.guru', 'Binky Moon, LLC'),

					 ('generic', '.gw', 'Autoridade Reguladora Nacional - Tecnologias de Informação e Comunicação da Guiné-Bissau'),

					 ('generic', '.gy', 'University of Guyana'),

					 ('generic', '.hamburg', 'Hamburg Top-Level-Domain GmbH'),

					 ('generic', '.hangout', 'Charleston Road Registry Inc.'),

					 ('generic', '.haus', 'United TLD Holdco Ltd.'),

					 ('generic', '.hbo', 'HBO Registry Services, Inc.'),

					 ('generic', '.hdfc', 'HOUSING DEVELOPMENT FINANCE CORPORATION LIMITED'),

					 ('generic', '.hdfcbank', 'HDFC Bank Limited'),

					 ('generic', '.health', 'DotHealth, LLC'),

					 ('generic', '.healthcare', 'Binky Moon, LLC'),

					 ('generic', '.help', 'Uniregistry, Corp.'),

					 ('generic', '.helsinki', 'City of Helsinki'),

					 ('generic', '.here', 'Charleston Road Registry Inc.'),

					 ('generic', '.hermes', 'Hermes International'),

					 ('generic', '.hgtv', 'Lifestyle Domain Holdings, Inc.'),

					 ('generic', '.hiphop', 'Uniregistry, Corp.'),

					 ('generic', '.hisamitsu', 'Hisamitsu Pharmaceutical Co.,Inc.'),

					 ('generic', '.hitachi', 'Hitachi, Ltd.'),

					 ('generic', '.hiv', 'Uniregistry, Corp.'),

					 ('generic', '.hk', 'Hong Kong Internet Registration Corporation Ltd.'),

					 ('generic', '.hkt', 'PCCW-HKT DataCom Services Limited'),

					 ('generic', '.hm', 'HM Domain Registry'),

					 ('generic', '.hn', 'Red de Desarrollo Sostenible Honduras'),

					 ('generic', '.hockey', 'Binky Moon, LLC'),

					 ('generic', '.holdings', 'Binky Moon, LLC'),

					 ('generic', '.holiday', 'Binky Moon, LLC'),

					 ('generic', '.homedepot', 'Home Depot Product Authority, LLC'),

					 ('generic', '.homegoods', 'The TJX Companies, Inc.'),

					 ('generic', '.homes', 'DERHomes, LLC'),

					 ('generic', '.homesense', 'The TJX Companies, Inc.'),

					 ('generic', '.honda', 'Honda Motor Co., Ltd.'),

					 ('generic', '.horse', 'Binky Moon, LLC'),

					 ('generic', '.hospital', 'Binky Moon, LLC'),

					 ('generic', '.host', 'DotHost Inc.'),

					 ('generic', '.hosting', 'United TLD Holdco Ltd.'),

					 ('generic', '.hot', 'Amazon Registry Services, Inc.'),

					 ('generic', '.hoteles', 'Travel Reservations SRL'),

					 ('generic', '.hotels', 'Booking.com B.V.'),

					 ('generic', '.hotmail', 'Microsoft Corporation'),

					 ('generic', '.house', 'Binky Moon, LLC'),

					 ('generic', '.how', 'Charleston Road Registry Inc.'),

					 ('generic', '.hr', 'CARNet - Croatian Academic and Research Network'),

					 ('generic', '.hsbc', 'HSBC Global Services');
					                """;

			stmt.execute(insertDefaults);
		}
	}
}
