--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.0

-- Started on 2024-01-14 16:56:29 EET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3671 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 18009)
-- Name: summary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summary (
    date date NOT NULL,
    total real,
    id integer NOT NULL
);


ALTER TABLE public.summary OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 18012)
-- Name: summary_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.summary_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.summary_id_seq OWNER TO postgres;

--
-- TOC entry 3672 (class 0 OID 0)
-- Dependencies: 223
-- Name: summary_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.summary_id_seq OWNED BY public.summary.id;


--
-- TOC entry 220 (class 1259 OID 17755)
-- Name: suppliers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.suppliers (
    id integer NOT NULL,
    name character varying NOT NULL,
    address character varying,
    phone_number character varying,
    cui character varying NOT NULL
);


ALTER TABLE public.suppliers OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17758)
-- Name: suppliers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.suppliers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.suppliers_id_seq OWNER TO postgres;

--
-- TOC entry 3673 (class 0 OID 0)
-- Dependencies: 221
-- Name: suppliers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.suppliers_id_seq OWNED BY public.suppliers.id;


--
-- TOC entry 224 (class 1259 OID 18044)
-- Name: suppliers_transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.suppliers_transactions (
    transaction_id integer NOT NULL,
    supplier_id integer NOT NULL
);


ALTER TABLE public.suppliers_transactions OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17721)
-- Name: transaction_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction_details (
    price real NOT NULL,
    tva integer NOT NULL,
    price_no_tva real,
    discount real NOT NULL,
    total real NOT NULL,
    transactionid integer,
    transactiondetailsid integer NOT NULL,
    CONSTRAINT non_negative_discount CHECK ((discount >= (0)::double precision)),
    CONSTRAINT non_negative_total CHECK ((total >= (0)::double precision)),
    CONSTRAINT positive_price CHECK ((price >= (0)::double precision))
);


ALTER TABLE public.transaction_details OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17748)
-- Name: transaction_details_transactiondetailsid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_details_transactiondetailsid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transaction_details_transactiondetailsid_seq OWNER TO postgres;

--
-- TOC entry 3674 (class 0 OID 0)
-- Dependencies: 219
-- Name: transaction_details_transactiondetailsid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transaction_details_transactiondetailsid_seq OWNED BY public.transaction_details.transactiondetailsid;


--
-- TOC entry 217 (class 1259 OID 17550)
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transactions (
    amount numeric(10,2) NOT NULL,
    transaction_date date DEFAULT CURRENT_TIMESTAMP,
    description character varying(255),
    measure_unit character varying,
    supplier_name character varying,
    id integer NOT NULL
);


ALTER TABLE public.transactions OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17524)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    password character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    registration_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 17523)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3675 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 3493 (class 2604 OID 18013)
-- Name: summary id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summary ALTER COLUMN id SET DEFAULT nextval('public.summary_id_seq'::regclass);


--
-- TOC entry 3492 (class 2604 OID 17759)
-- Name: suppliers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers ALTER COLUMN id SET DEFAULT nextval('public.suppliers_id_seq'::regclass);


--
-- TOC entry 3491 (class 2604 OID 17749)
-- Name: transaction_details transactiondetailsid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_details ALTER COLUMN transactiondetailsid SET DEFAULT nextval('public.transaction_details_transactiondetailsid_seq'::regclass);


--
-- TOC entry 3488 (class 2604 OID 17527)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3663 (class 0 OID 18009)
-- Dependencies: 222
-- Data for Name: summary; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.summary (date, total, id) FROM stdin;
2024-01-13	0	67
2024-01-05	60	68
2024-01-14	2.97	72
2024-01-29	0	75
2024-01-06	44	76
2024-01-12	613.26	77
2024-01-07	90	79
2024-01-09	134.09	83
2024-10-01	0	84
\.


--
-- TOC entry 3661 (class 0 OID 17755)
-- Dependencies: 220
-- Data for Name: suppliers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.suppliers (id, name, address, phone_number, cui) FROM stdin;
15	Dedeman	blv Mihai Viteazul nr 81	071982618	54123123
16	Carrefour	blv Mihai Viteazul nr 90A	071239721	3123123
17	Noriel	Zalau Value Center	071237129	2133123
19	Compass	blv Mihai VIteazul 1	0728491023	31238419
20	Altex	blv Mihai Viteazul 20	0718294817	312341241
21	UTCN	str. Observator	0260481729	23124123
\.


--
-- TOC entry 3665 (class 0 OID 18044)
-- Dependencies: 224
-- Data for Name: suppliers_transactions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.suppliers_transactions (transaction_id, supplier_id) FROM stdin;
3	16
5	16
1	15
2	15
4	17
6	15
7	15
9	16
1	16
\.


--
-- TOC entry 3659 (class 0 OID 17721)
-- Dependencies: 218
-- Data for Name: transaction_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction_details (price, tva, price_no_tva, discount, total, transactionid, transactiondetailsid) FROM stdin;
20	19	16.2	0	20	3	77
25	19	20.25	0	50	1	81
100	19	81	10	180	4	91
3	19	2.43	1	2.97	5	92
24	19	19.44	0	24	6	93
249	19	201.69	13	433.26	7	94
39	19	31.59	10	35.1	8	97
8.99	19	7.2819	0	8.99	9	98
100	19	81	10	90	11	99
100	19	81	10	90	12	100
\.


--
-- TOC entry 3658 (class 0 OID 17550)
-- Dependencies: 217
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transactions (amount, transaction_date, description, measure_unit, supplier_name, id) FROM stdin;
1.00	2024-01-09	Laptop	buc	Altex	8
1.00	2024-01-09	Coca-cola	buc	Carrefour	9
1.00	2024-01-09	coca	l	Altex	10
1.00	2024-01-09	1	1	Altex	11
2.00	2024-01-05	Piulite	buc	Carrefour	1
1.00	2024-01-10	Apa plata	l	Carrefour	12
1.00	2024-01-05	Tub PVC	m	Dedeman	2
1.00	2024-01-06	Lapte 3.5%	L	Carrefour	3
2.00	2024-01-12	Masina Jucarie politie	buc	Noriel	4
1.00	2024-01-14	Sfoara	m	Carrefour	5
1.00	2024-01-06	Ghiveci	buc	Dedeman	6
2.00	2024-01-12	Chiuveta	buc	Dedeman	7
\.


--
-- TOC entry 3657 (class 0 OID 17524)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, password, email, registration_date) FROM stdin;
22	ionutcornea	icornea76@yahoo.com	2024-01-05 16:29:46.559515
\.


--
-- TOC entry 3676 (class 0 OID 0)
-- Dependencies: 223
-- Name: summary_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.summary_id_seq', 84, true);


--
-- TOC entry 3677 (class 0 OID 0)
-- Dependencies: 221
-- Name: suppliers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.suppliers_id_seq', 21, true);


--
-- TOC entry 3678 (class 0 OID 0)
-- Dependencies: 219
-- Name: transaction_details_transactiondetailsid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_details_transactiondetailsid_seq', 100, true);


--
-- TOC entry 3679 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 24, true);


--
-- TOC entry 3508 (class 2606 OID 18039)
-- Name: summary summary_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summary
    ADD CONSTRAINT summary_un UNIQUE (total, date);


--
-- TOC entry 3504 (class 2606 OID 17767)
-- Name: suppliers suppliers_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT suppliers_pk PRIMARY KEY (id);


--
-- TOC entry 3502 (class 2606 OID 17754)
-- Name: transaction_details transaction_details_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_details
    ADD CONSTRAINT transaction_details_pk PRIMARY KEY (transactiondetailsid);


--
-- TOC entry 3500 (class 2606 OID 17998)
-- Name: transactions transactions_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pk PRIMARY KEY (id);


--
-- TOC entry 3506 (class 2606 OID 17957)
-- Name: suppliers unique_supplier_name; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT unique_supplier_name UNIQUE (name);


--
-- TOC entry 3498 (class 2606 OID 17532)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3509 (class 2606 OID 17958)
-- Name: transactions fk_suppliers; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT fk_suppliers FOREIGN KEY (supplier_name) REFERENCES public.suppliers(name);


--
-- TOC entry 3511 (class 2606 OID 18047)
-- Name: suppliers_transactions suppliers_transactions_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers_transactions
    ADD CONSTRAINT suppliers_transactions_fk FOREIGN KEY (transaction_id) REFERENCES public.transactions(id);


--
-- TOC entry 3512 (class 2606 OID 18052)
-- Name: suppliers_transactions suppliers_transactions_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers_transactions
    ADD CONSTRAINT suppliers_transactions_fk_1 FOREIGN KEY (supplier_id) REFERENCES public.suppliers(id);


--
-- TOC entry 3510 (class 2606 OID 18004)
-- Name: transaction_details transaction_details_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_details
    ADD CONSTRAINT transaction_details_fk FOREIGN KEY (transactionid) REFERENCES public.transactions(id) ON DELETE CASCADE;


-- Completed on 2024-01-14 16:56:29 EET

--
-- PostgreSQL database dump complete
--

