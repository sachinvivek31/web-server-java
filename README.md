# Java Web Server

This project implements a web server in Java with three different approaches for handling client requests:

1. **Single-threaded Server**
2. **Multi-threaded Server**
3. **Thread Pool Server**

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)

## Overview

This Java-based web server project demonstrates three types of server architectures:

- **Single-threaded Server**: A simple server that handles one request at a time. It waits for the current request to complete before accepting the next one.
- **Multi-threaded Server**: A server that creates a new thread for each incoming client request, allowing concurrent request handling.
- **Thread Pool Server**: A more efficient approach where a pool of threads is pre-allocated to handle multiple requests concurrently.

Each server implementation is contained in different directories within the project:

- `singleThreaded/`
- `multiThreaded/`
- `threadPool/`

## Features

- Simple HTTP server implementation using Java Sockets
- Three types of server architectures to choose from
- Supports basic client-server communication
- Easy to extend and customize for additional features like logging, SSL/TLS support, etc.

## Usage

- class files are already present
- just run file using java Server (example) and try jmeter for testing
- tested with 1000rps, threadpool worked nicely

Thanks
