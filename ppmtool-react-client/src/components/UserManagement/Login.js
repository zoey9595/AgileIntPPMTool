import React, { Component } from "react";
import { Link } from "react-router-dom";
import { login } from "../../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class Login extends Component {
  constructor() {
    super();

    this.state = {
      username: "",
      password: "",
      errors: {},
    };

    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    if (nextProps.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const loginRequest = {
      username: this.state.username,
      password: this.state.password,
    };
    this.props.login(loginRequest);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="login">
        <div className="container">
          <div className="row">
            <div className="col-md-5 text-center register-side">
              <h1 className="mb-5">Hello, Friend!</h1>
              <p className="mb-5">
                Enter your personal details and start journey with us
              </p>
              <Link to="/register" className="btn btn-lg btn-ghost">
                Sign Up
              </Link>
            </div>
            <div className="col-md-7 login-side">
              <h1 className="text-center mb-5">Log In</h1>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.username,
                    })}
                    placeholder="Email Address (Username)"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChange}
                  />
                  {errors.username && (
                    <div className="invalid-feedback">{errors.username}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.password,
                    })}
                    placeholder="Password"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChange}
                  />
                  {errors.password && (
                    <div className="invalid-feedback">{errors.password}</div>
                  )}
                </div>
                <div className="text-center">
                  <input
                    type="submit"
                    className="btn btn-lg start-button mt-4"
                  />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Login.propTypes = {
  errors: PropTypes.object.isRequired,
  login: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
  errors: state.errors,
});

export default connect(mapStateToProps, { login })(Login);
