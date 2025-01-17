/* eslint-disable no-undef */
import userConstants from '../constants/user-constants';
import authenticationReducer from './authentication-reducer';

describe('>>> Authentication reducer tests', () => {

    it('should return default state in the default action', () => {
        expect(authenticationReducer()).toEqual({ sessionOn: false });
    });

    it('should handle USERS_LOGIN_REQUEST', () => {
        const action = {
            type: userConstants.USERS_LOGIN_REQUEST,
            user: "user",
        }
        expect(authenticationReducer({}, action)).toEqual({ user: "user" });
    });

    it('should handle USERS_LOGIN_SUCCESS', () => {
        const action = {
            type: userConstants.USERS_LOGIN_SUCCESS,
            user: "user",
        }
        expect(authenticationReducer({}, action)).toEqual({ error: null, user: "user", showHeader: true });
        expect(authenticationReducer()).toEqual({ sessionOn: true });
    });

    it('should handle USERS_LOGIN_FAILURE', () => {
        const action = {
            type: userConstants.USERS_LOGIN_FAILURE,
            error: "error",
        }
        expect(authenticationReducer({}, action)).toEqual({ error: "error" });
        expect(authenticationReducer()).toEqual({ sessionOn: true });
    });

    it('should handle AUTHENTICATION_FAILURE', () => {
        const action = {
            type: userConstants.AUTHENTICATION_FAILURE,
            error: "error",
        }
        const result = authenticationReducer({}, action);
        expect(result.error).toEqual("error");
        expect(result.sessionOn).toEqual(true);
        expect(authenticationReducer()).toEqual({ sessionOn: true });
        result.onCompleteHandling();
        expect(authenticationReducer()).toEqual({ sessionOn: false });
    });

    it('should handle USERS_LOGOUT_REQUEST', () => {
        // Login again to recover from previous test case
        authenticationReducer({}, { type: userConstants.USERS_LOGIN_SUCCESS });

        const result = authenticationReducer({}, { type: userConstants.USERS_LOGOUT_REQUEST });
        expect(result.error).toEqual(null);
        expect(result.showHeader).toEqual(false);
        expect(authenticationReducer()).toEqual({ sessionOn: true });
        result.onCompleteHandling();
        expect(authenticationReducer()).toEqual({ sessionOn: false });
    });

    it('should handle USERS_LOGOUT_SUCCESS', () => {
        // Login again to recover from previous test case
        authenticationReducer({}, { type: userConstants.USERS_LOGIN_SUCCESS });

        const result = authenticationReducer({}, { type: userConstants.USERS_LOGOUT_SUCCESS });
        expect(result.error).toEqual(null);
        expect(result.showHeader).toEqual(false);
        expect(authenticationReducer()).toEqual({ sessionOn: true });
        result.onCompleteHandling();
        expect(authenticationReducer()).toEqual({ sessionOn: false });
    });
    
    it('should handle USERS_LOGOUT_FAILURE', () => {
        const action = {
            type: userConstants.USERS_LOGOUT_FAILURE,
            error: "error",
        }
        expect(authenticationReducer({}, action)).toEqual({ error: "error", showHeader: false });
        expect(authenticationReducer()).toEqual({ sessionOn: false });
    });

});