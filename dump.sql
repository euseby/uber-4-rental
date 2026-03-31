--
-- PostgreSQL database dump
--

\restrict 7AWigONaKDZrSneB1b1xk5utzLZPAPmcKUdji3eKTXay7kyrbMpfVJ95xQ8A6Ti

-- Dumped from database version 15.17 (Debian 15.17-1.pgdg13+1)
-- Dumped by pg_dump version 15.17 (Debian 15.17-1.pgdg13+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bookings; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.bookings (
    id bigint NOT NULL,
    end_date character varying(255),
    start_date character varying(255),
    status character varying(255),
    user_id bigint,
    vehicle_id bigint
);


ALTER TABLE public.bookings OWNER TO "user";

--
-- Name: bookings_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.bookings_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bookings_seq OWNER TO "user";

--
-- Name: ratings; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.ratings (
    id bigint NOT NULL,
    comment character varying(255),
    score integer NOT NULL,
    user_id bigint,
    vehicle_id bigint
);


ALTER TABLE public.ratings OWNER TO "user";

--
-- Name: ratings_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.ratings_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ratings_seq OWNER TO "user";

--
-- Name: users; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255),
    address character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    license_expiry character varying(255),
    license_number character varying(255),
    phone character varying(255),
    bio character varying(255)
);


ALTER TABLE public.users OWNER TO "user";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO "user";

--
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_seq OWNER TO "user";

--
-- Name: vehicles; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.vehicles (
    id bigint NOT NULL,
    brand character varying(255),
    fabr_year integer NOT NULL,
    image_url character varying(255),
    location character varying(255),
    model character varying(255),
    price_per_day double precision NOT NULL,
    rating double precision NOT NULL,
    type character varying(255)
);


ALTER TABLE public.vehicles OWNER TO "user";

--
-- Name: vehicles_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.vehicles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicles_id_seq OWNER TO "user";

--
-- Name: vehicles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.vehicles_id_seq OWNED BY public.vehicles.id;


--
-- Name: vehicles_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.vehicles_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicles_seq OWNER TO "user";

--
-- Name: vehicles id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.vehicles ALTER COLUMN id SET DEFAULT nextval('public.vehicles_id_seq'::regclass);


--
-- Data for Name: bookings; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.bookings (id, end_date, start_date, status, user_id, vehicle_id) FROM stdin;
1	\N	2026-05-20	Confirmed	5	1
\.


--
-- Data for Name: ratings; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.ratings (id, comment, score, user_id, vehicle_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.users (id, email, password, role, username, address, first_name, last_name, license_expiry, license_number, phone, bio) FROM stdin;
1	admin@uber4rental.com	MetamindGreen	ADMIN	\N	\N	\N	\N	\N	\N	\N	\N
4	test@test.com	test1234	business	test	\N	\N	\N	\N	\N	\N	\N
3	anacampioana@gmail.com	Test1234	client	\N	\N	\N	\N	\N	\N	\N	\N
5	marius@demo.com	$2a$10$vuIpLUC8tRPkDfwT2wum3.w.xCM7b7cEPmVoYGQw252DXP6fTkW..	client	marius	Bucuresti, Sector 1	Marius	Popescu	2030-10-10	B123456	0744123458	Imi place sa conduc masini sigure.
6	mario1@demo.com	$2a$10$Xan12MqvWKXVWVuOwUrtI.EuynLB8.b75L4CZp6DV738ZjFlfn5Ja	client	mario	\N	\N	\N	\N	\N	\N	\N
7	mario@demo.com	$2a$10$9sKJ2Fun10svC3.gp9zN6usjfwxCzP9OzsX5AhQNYr/12AtLPNopC	client	mario	\N	\N	\N	\N	\N	\N	\N
9	euseby779@gmail.com	$2a$10$PiSPW81VD.vcG1FoIbLR/Oan5yif8weN2WnGLGIv00XmvW5KOUq.O	business	euseby	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Data for Name: vehicles; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.vehicles (id, brand, fabr_year, image_url, location, model, price_per_day, rating, type) FROM stdin;
1	Tesla	2023	https://images.unsplash.com/photo-1560958089-b8a1929cea89?auto=format&fit=crop&w=800&q=80	Downtown	Model 3	85	4.9	Electric
2	BMW	2022	https://images.unsplash.com/photo-1556189250-72ba954cfc2b?auto=format&fit=crop&w=800&q=80	Uptown	X5	110	4.8	SUV
3	Toyota	2021	https://images.unsplash.com/photo-1590362891991-f776e747a588?auto=format&fit=crop&w=800&q=80	Airport	Corolla	35	4.7	Sedan
4	Mercedes-Benz	2022	https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?auto=format&fit=crop&w=800&q=80	Midtown	C-Class	95	4.9	Sedan
5	Ford	2021	https://images.unsplash.com/photo-1584345604476-8ec5e12e42a5?auto=format&fit=crop&w=800&q=80	Downtown	Mustang	120	4.8	Coupe
6	Honda	2020	https://images.unsplash.com/photo-1604147706283-d7119b5b822c?auto=format&fit=crop&w=800&q=80	Suburbs	CR-V	50	4.6	SUV
\.


--
-- Name: bookings_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.bookings_seq', 1, false);


--
-- Name: ratings_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.ratings_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.users_id_seq', 9, true);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.users_seq', 151, true);


--
-- Name: vehicles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.vehicles_id_seq', 6, true);


--
-- Name: vehicles_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.vehicles_seq', 1, false);


--
-- Name: bookings bookings_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.bookings
    ADD CONSTRAINT bookings_pkey PRIMARY KEY (id);


--
-- Name: ratings ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT ratings_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: vehicles vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

\unrestrict 7AWigONaKDZrSneB1b1xk5utzLZPAPmcKUdji3eKTXay7kyrbMpfVJ95xQ8A6Ti

