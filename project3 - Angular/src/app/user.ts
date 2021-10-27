export interface IUserAccount{
    userId: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    birthday: number;
    statusText: string;
    profilePicture: ImageBitmap;
    postList: string[];
    dittoFollowerList: string[];
    dittoFollowingList: string[];

}